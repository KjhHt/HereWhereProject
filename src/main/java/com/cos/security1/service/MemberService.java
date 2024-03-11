package com.cos.security1.service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cos.security1.service.dto.BoardDto;
import com.cos.security1.service.dto.ChatDto;
import com.cos.security1.service.dto.CommentDto;
import com.cos.security1.service.dto.FollowDto;
import com.cos.security1.service.dto.LikeDto;
import com.cos.security1.service.dto.LocationDto;
import com.cos.security1.service.dto.NoticeDto;
import com.cos.security1.service.dto.ReservationDto;
import com.cos.security1.service.dto.UserDto;
import com.cos.security1.util.FileUtils;
import com.cos.security1.util.JWTTokens;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

@Service
public class MemberService {

	@Autowired
	private MybatisMapper mapper;

    public boolean findbyUserPassword(Map map) {
    	String pwd = mapper.findbyUserPassword(map);
    	if(pwd!=null) {
    	    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    	    boolean passwordMatch = passwordEncoder.matches(map.get("pwd").toString(), pwd);
    	    return passwordMatch;
    	}
        return false;
    }

	public Map<String, Object> getInfo(String id) {
		return mapper.getInfo(id);
	}

	public boolean isPreviousUser(String id) {
		int ismember = mapper.isPreviousUser(id);
		return ismember > 0;
	}

	public UserDto findByUsername(String id) {
		return mapper.findByUsername(id);
	}

	public void joinSocialMember(UserDto dto) {
		BCryptPasswordEncoder bcy = new BCryptPasswordEncoder();
		String pwd = bcy.encode("소셜로그인");
		dto.setPwd(pwd);
		mapper.joinSocialMember(dto);
	}

	public UserDto findByMemberInfo(String id) {
		return mapper.findByMemberInfo(id);
	}

	//회원가입
	public void joinMember(UserDto dto) {
		// 비밀번호 인코딩
		BCryptPasswordEncoder bcy = new BCryptPasswordEncoder();
		String pwd = bcy.encode(dto.getPwd());
		dto.setPwd(pwd);
		
		// member 테이블
		mapper.joinMember(dto);
		// member_info 테이블
		mapper.joinMemberInfo(dto);
		// profileimage_table 테이블
		mapper.profileImageTable(dto);
	}

	public void updateRecentLoginTime(String id) {
		mapper.updateRecentLoginTime(id);
	}

	public List<BoardDto> boardList(String num, HttpServletRequest request, String keyword) throws IOException {
		List<BoardDto> list = mapper.boardList(num,keyword);
		for(BoardDto value : list) {
			value.setProfileimage(convertProfileImageToBase64(value.getProfileimage(), request));
			value.setBase64BoardImages(convertBoardImagesToBase64(value.getBoard_imageFileName(), request));
		    //태그 (,)구분자로 리스트로 만들어서 전달!
		    String boardTags = value.getBoard_tags();
		    if(boardTags == null) {
		    	//null 일때는 빈리스트 전달
		    	value.setBoardTags(new String[0]);
		    }
		    else {
		    	String[] splitBoardTags = boardTags.split(",");
		    	value.setBoardTags(splitBoardTags);
		    }
		    String html = value.getBoard_content();
		    String text = Jsoup.parse(html).text();
		    value.setBoard_content(text);
		    // 좋아요 누른사람들의 데이타 가져가보자.. (닉네임, 프로필이미지)
		    List<LikeDto> likeList = mapper.getLikeList(value.getBoard_no());
		    for(LikeDto likeValue : likeList) {
		    	likeValue.setProfileimage(convertProfileImageToBase64(likeValue.getProfileimage(),request));
		    }
		    value.setLikeList(likeList);
		    
		    // 음.. 지역정보가 있는 테이블인지도 넣어주자!
		    LocationDto locationDto = mapper.getLocationData(value.getBoard_no());
		    value.setLocationList(locationDto);
		}
		return list;
	}

	public List<BoardDto> myBoard(String id, HttpServletRequest request) throws IOException {
		List<BoardDto> list = mapper.myBoard(id);
		for(BoardDto value : list) {
			value.setProfileimage(convertProfileImageToBase64(value.getProfileimage(), request));
			value.setBase64BoardImages(convertBoardImagesToBase64(value.getBoard_imageFileName(), request));
		    //태그 (,)구분자로 리스트로 만들어서 전달!
		    String boardTags = value.getBoard_tags();
		    if(boardTags == null) {
		    	//null 일때는 빈리스트 전달
		    	value.setBoardTags(new String[0]);
		    }
		    else {
		    	String[] splitBoardTags = boardTags.split(",");
		    	value.setBoardTags(splitBoardTags);
		    }
		    String html = value.getBoard_content();
		    String text = Jsoup.parse(html).text();
		    value.setBoard_content(text);
		    // 좋아요 누른사람들의 데이타 가져가보자.. (닉네임, 프로필이미지)
		    List<LikeDto> likeList = mapper.getLikeList(value.getBoard_no());
		    for(LikeDto likeValue : likeList) {
		    	likeValue.setProfileimage(convertProfileImageToBase64(likeValue.getProfileimage(),request));
		    }
		    value.setLikeList(likeList);
		    
		    // 음.. 지역정보가 있는 테이블인지도 넣어주자!
		    LocationDto locationDto = mapper.getLocationData(value.getBoard_no());
		    value.setLocationList(locationDto);
		}
		
		return list;
	}

	
	public String convertProfileImageToBase64(String profileImage, HttpServletRequest req) throws IOException {
	    //프로필 이미지가 서버에 저장이 되어있을때
	    if( profileImage.startsWith("D:") || profileImage.startsWith("E:") ) {
	        //여기에서 BASE64로 넣자
	        String[] splitProfile = profileImage.split("\\\\");
	        String profileImageName = splitProfile[splitProfile.length-1];
	        String imagePath = req.getServletContext().getRealPath("/upload/profile/")+profileImageName.trim();
	        //프로필 사진 base64로 변경
	        return imageToBase64(imagePath);
	    }
	    return profileImage;
	}
	
	public String[] convertBoardImagesToBase64(String boardImageFileNames, HttpServletRequest req) throws IOException {
	    if (boardImageFileNames != null) {
	        String[] splitFileNames = boardImageFileNames.split(",");
	        String[] base64BoardImages = new String[splitFileNames.length];
	        for (int i = 0; i < splitFileNames.length; i++) {
	            String fileName = splitFileNames[i];
	            String imagePath = req.getServletContext().getRealPath("/upload/board/")+fileName.trim();
	            base64BoardImages[i] = imageToBase64(imagePath);
	        }
	        return base64BoardImages;
	    }
	    return null;
	}
	
	public BoardDto boardInsert(BoardDto dto, HttpServletRequest req, LocationDto locationdto) throws IOException, ServletException {
		
		String saveDirectory = req.getServletContext().getRealPath("/upload/board/");
		Collection<Part> parts = req.getParts();
		StringBuffer filenames = FileUtils.upload(parts, saveDirectory);
		dto.setBoard_imageFileName(filenames.toString());
		mapper.boardInsert(dto);
		
		String fileName = filenames.toString();
		BoardDto returnDto = mapper.findBoardNo(fileName);
		//여기서 프로필이미지를 전달한다!
		returnDto.setProfileimage(dto.getProfileimage());
		
		returnDto.setBoard_imageFileName(fileName);
		String[] splitFilenames = filenames.toString().split(",");
		int index = 0;
		for (Part part : parts) {
		    if (part.getContentType() != null) { // 파일인 경우
		        long size = part.getSize();
		        String filesize = String.valueOf(size);
		        String filename = splitFilenames[index];
		        BoardDto imageDto = new BoardDto();
		        imageDto.setBoardImage_FileName(filename);
		        imageDto.setBoardImage_Size(filesize);
		        imageDto.setBoardImage_Route("/upload/board/"+filename);
		        imageDto.setBoard_no(returnDto.getBoard_no());
		        mapper.boardImageInsert(imageDto);
			    index++;
		    }
		}
		
		//지역 테이블 정보가 있을 때
		if(locationdto.getIsLocation() == null) {
			System.out.println("지역정보가 있을 때 !");
			locationdto.setBoard_no(returnDto.getBoard_no());
			mapper.insertBoardLocation(locationdto);
		}
		
		//프로필이미지와 게시판이미지 base64처리,
		returnDto.setProfileimage(convertProfileImageToBase64(returnDto.getProfileimage(),req));
		returnDto.setBase64BoardImages(convertBoardImagesToBase64(returnDto.getBoard_imageFileName(),req));
		returnDto.setLike_count("0");
		return returnDto;
	}

	//이미지 base64로 변경하는 메소드
	public String imageToBase64(String imagePath) throws IOException {
	    Path imagePathInFileSystem = Paths.get(imagePath);
	    Resource resource = new UrlResource(imagePathInFileSystem.toUri());
	    byte[] imageBytes = resource.getInputStream().readAllBytes();
	    return Base64.encodeBase64String(imageBytes);
	}
	
	public List<CommentDto> commentList(String board_no,HttpServletRequest request) throws IOException {
		
    	String token = null;
        String id = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("User-Token".equals(cookie.getName())) {
                    String cookieValue = cookie.getValue();
                    token = cookieValue;
                }
            }
        }
        if (token != null) { // 토큰이 있는 경우에만 username을 추출
            Map username = JWTTokens.getTokenPayloads(token);
            id = (String)username.get("username");
        }
        else { // 비회원일 경우
        	id = "guest";
        }
		
		List<CommentDto> list = mapper.commentList(board_no,id);
		for(CommentDto value : list) {
			value.setProfileimage(convertProfileImageToBase64(value.getProfileimage(), request));
		}
		return list;
	}

	public void commentInsert(CommentDto commentDto) {
		mapper.commentInsert(commentDto);
	}

	public boolean islike(String id,String board_no) {
		int like = mapper.islike(id,board_no);
		// 좋아요 삭제/등록 구현
		if(like==0) { // 등록된거없음(등록)
			mapper.insertLike(id,board_no);
			return true;
		}
		else { // 등록된거있음(삭제)
			mapper.deleteLike(id,board_no);
			return false;
		}
	}

	public boolean isBoardLike(String id, String board_no) {
		int like = mapper.islike(id,board_no);
		if(like==0) { // 등록된거없음
			return false;
		}
		else { // 등록된거있음
			return true;
		}
	}

	public String findTokenByUserId(String id) {
		return mapper.findTokenByUserId(id);
	}

	public void deletePushToken(String id) {
		mapper.deletePushToken(id);
	}

	public List<FollowDto> getFollowList(String id) {
		return mapper.getFollowList(id);
	}

	public boolean findById(String id) {
		int count = mapper.findById(id);
		if(count == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public String getDmNo(String id, String rid) {
		ChatDto dto = mapper.getDmNo(id,rid);
		String dm_no = "";
		if(dto != null) {
			dm_no = dto.getDm_no();
			return dm_no;
		}
		else {
			mapper.insertChatRoom(id,rid);
			dm_no = mapper.getSeqDmNo();
			return dm_no;
		}
		
	}

	public List<ChatDto> chatList(String dm_no) {
		return mapper.chatList(dm_no);
	}

	public void insertMessage(ChatDto chatdto) {
		mapper.insertMessage(chatdto);		
	}

	public boolean checkFollowRequest(FollowDto followRequest) {
		int count = mapper.checkFollowRequest(followRequest);
		if(count != 0 ) 
			return true;
		else
			return false;
	}

	public void insertFollow(FollowDto followRequest) {
		mapper.insertFollow(followRequest);	
	}

	public List<NoticeDto> noticeList(String id, HttpServletRequest req) throws IOException {
		
		List<NoticeDto> list = mapper.noticeList(id);
		for(NoticeDto value : list) {
			value.setProfileimage(convertProfileImageToBase64(value.getProfileimage(),req));
		}
		
		return list;
	}

	public void insertNotice(NoticeDto dto) {
		mapper.insertNotice(dto);
	}

	public String getNoticeNo(FollowDto followRequest) {
		// TODO Auto-generated method stub
		return mapper.getNoticeNo(followRequest);
	}

	public void successFollow(String notice_no) {
		mapper.successFollow(notice_no);
	}

	public void FailFollow(String notice_no) {
		mapper.FailFollow(notice_no);
	}

	public void deleteNotice(String notice_no) {
		mapper.deleteNotice(notice_no);
	}

	public void insertReservation(ReservationDto reservationDto) {
		mapper.insertReservation(reservationDto);
	}

	public void addCommentLike(String comment_no, String id) {
		mapper.addCommentLike(comment_no,id);
	}

	public void deleteCommentLike(String comment_no, String id) {
		mapper.deleteCommentLike(comment_no,id);
	}

	public List<FollowDto> getIsFollowList(String id) {
		return mapper.getIsFollowList(id);
	}

	public List<ReservationDto> findReservationsByUserId(String id) {
		return mapper.findReservationsByUserId(id);
	}

	public void insertFlightReservation(ReservationDto reservationDto) {
		mapper.insertFlightReservation(reservationDto);
	}

	public List<ReservationDto> findFlightReservationsByUserId(String id) {
		return mapper.findFlightReservationsByUserId(id);
	}

	public UserDto getMypageHeader(String id,HttpServletRequest request) throws IOException {
		
		UserDto list = mapper.getMypageHeader(id);
		
		list.setProfileimage(convertProfileImageToBase64(list.getProfileimage(), request));
		
		return list;
	}

	public List<FollowDto> getMyFollowList(String id, HttpServletRequest request) throws IOException {
		List<FollowDto> list = mapper.getMyFollowList(id);
		for(FollowDto value : list) {
			value.setProfileImage(convertProfileImageToBase64(value.getProfileImage(), request));
		}
		return list;
	}




    

	
}
