package com.cos.security1.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.cos.security1.service.MemberService;
import com.cos.security1.service.dto.BoardDto;
import com.cos.security1.service.dto.ChatDto;
import com.cos.security1.service.dto.CommentDto;
import com.cos.security1.service.dto.FollowDto;
import com.cos.security1.service.dto.NoticeDto;
import com.cos.security1.service.dto.ReservationDto;
import com.cos.security1.service.dto.UserDto;
import com.cos.security1.util.FileUtils;
import com.cos.security1.util.JWTTokens;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

@org.springframework.web.bind.annotation.RestController
public class RestController {
	
	@Autowired
	private MemberService service;	
	
	@GetMapping("/user/test")
	public String userTest() {
		System.out.println("user에 접근함");
		// 문자 메세지~~
//		DefaultMessageService messageService =  NurigoApp.INSTANCE.initialize("NCSBOMLEW4XQYZAG", "EBSKN1NKGVNW9QZQQSMQYZMJZEBLDMUG", "http://api.coolsms.co.kr");
//		// Message 패키지가 중복될 경우 net.nurigo.sdk.message.model.Message로 치환하여 주세요
//		Message message = new Message();
//
//		message.setFrom("01097957567");
//		message.setTo("01020542040");
//		message.setText("문자 테스트,,,,,");
//
//		try {
//		  // send 메소드로 ArrayList<Message> 객체를 넣어도 동작합니다!
//		  messageService.send(message);
//		} catch (NurigoMessageNotReceivedException exception) {
//		  // 발송에 실패한 메시지 목록을 확인할 수 있습니다!
//		  System.out.println(exception.getFailedMessageList());
//		  System.out.println(exception.getMessage());
//		} catch (Exception e) {
//		  System.out.println(e.getMessage());
//		}
		return "user";
	}
	
	@GetMapping("/manager/test")
	public String managerTest() {
		System.out.println("manager에 접근함");
		return "manager";
	}
	
	@GetMapping("/admin/test")
	public String adminTest() {
		System.out.println("admin에 접근함");
		return "admin";
	}

	// 로그인 후 user권한으로 쿠키에있는 token전달
	@PostMapping("/user/getToken")
	public String getToken(HttpServletRequest request) {
		
		Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("User-Token".equals(cookie.getName())) {  // 쿠키의 이름이 "User-Token"인 경우
	                String cookieValue = cookie.getValue();
	                System.out.println("쿠키 값: " + cookieValue);
	                return cookieValue;
	            }
	        }
	    }
	    System.out.println("쿠키를 찾을 수 없습니다.");
		return "NoCookie";
	}
	
	// 토큰으로 이름(닉네임),프로필이미지 전달
	@GetMapping("/user/getMemberInfo")
	public UserDto getMemberInfo(HttpServletRequest request) {
		
		String token = null;
		Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("User-Token".equals(cookie.getName())) {  // 쿠키의 이름이 "User-Token"인 경우
	                String cookieValue = cookie.getValue();
	                token = cookieValue;
	            }
	        }
	    }
	    Map username = JWTTokens.getTokenPayloads(token);
	    String id = (String)username.get("username");
	    
		UserDto info = service.findByMemberInfo(id);
		service.updateRecentLoginTime(id);
		
		return info;
	}
	
	@GetMapping("/isSocialLogin")
	public String isSocialLogin(HttpServletRequest request) {
		
		String token = null;
		Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("User-Token".equals(cookie.getName())) {  // 쿠키의 이름이 "User-Token"인 경우
	                String cookieValue = cookie.getValue();
	                token = cookieValue;
	            }
	        }
	    }
		return token;
	}
	
	//회원가입
    @PostMapping("/joinMember")
    public String joinMember(
            @RequestPart("id") String id,
            @RequestPart("pwd") String pwd,
            @RequestPart("name") String name,
            @RequestPart("gender") String gender,
            @RequestPart("mbti") String mbti,
            @RequestPart("tel") String tel,
            @RequestPart("address") String address,
            @RequestPart("profileImage") MultipartFile profileImage,
            @RequestPart("lat") String lat,
            @RequestPart("lng") String lng,
            HttpServletRequest req
    ) throws IOException, ServletException {
        // 사용자 데이터 및 프로필 이미지 파일을 받아와 처리하는 로직을 작성
        long size = profileImage.getSize();
        String filesize = Long.toString(size);
        // 파일 미지정 시 회원가입페이지에서 디폴트 파일객체 보내주도록 설정!!
        String saveDirectory = req.getServletContext().getRealPath("/upload/profile/");
        //String saveDirectory = "/upload";
        System.out.println(saveDirectory);
		Collection<Part> parts = req.getParts();
		StringBuffer filenames = FileUtils.upload(parts, saveDirectory);
		UserDto dto = new UserDto();
		//수정해야함
		dto.setLat(lat);
		dto.setLng(lng);
		dto.setId(id); dto.setPwd(pwd); dto.setName(name); 
		dto.setGender( gender.substring(0, 1).toUpperCase() ); 
		dto.setMbti(mbti);
		dto.setTel(tel); dto.setAddress(address); dto.setProfileimage(saveDirectory+"\\"+filenames);
		dto.setFilename(filenames.toString()); dto.setFileroute(saveDirectory+"\\"+filenames);
		dto.setFilesize(filesize); dto.setAuthority("ROLE_USER"); dto.setProvider("WIT");
		service.joinMember(dto);
		
        return "회원가입 성공";
    }
	
    
    //게시판
    @GetMapping("/board")
    public List<BoardDto> board(@RequestParam String num,HttpServletRequest request) {
		
    	String token = null;
		Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("User-Token".equals(cookie.getName())) {  // 쿠키의 이름이 "User-Token"인 경우
	                String cookieValue = cookie.getValue();
	                token = cookieValue;
	            }
	        }
	    }
	    Map username = JWTTokens.getTokenPayloads(token);
	    String id = (String)username.get("username");
	    
    	System.out.println(id);
    	
	    List<BoardDto> dto = service.boardList(num);
	    
	    if(id==null) //비회원일때 좋아요 조회 아이디없으면 guest로 진행
	    	id = "guest";
	    
    	for(BoardDto value : dto) {
    		boolean like = service.isBoardLike(id, value.getBoard_no());
    		if(like) {
    			System.out.println("좋아요?");
    			value.setLike(true);
    		}
    	}
    	return dto;
    }
    
    //프로필 이미지불러오기
    @GetMapping("/profile/{imageName}")
    public ResponseEntity<String> getImage(@PathVariable String imageName,HttpServletRequest req) throws IOException {
    	
    	System.out.println("프로필 이미지 로직");
    	
    	String imagePath = req.getServletContext().getRealPath("/upload/profile/")+imageName;
        Path imagePathInFileSystem = Paths.get(imagePath);
        Resource resource = new UrlResource(imagePathInFileSystem.toUri());

        byte[] imageBytes = resource.getInputStream().readAllBytes();
        String base64Image = Base64.encodeBase64String(imageBytes);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(base64Image);
    }
    
    
    //게시판 이미지불러오기
    @GetMapping("/image/{imageName}")
    public ResponseEntity<Resource> boardImage(@PathVariable String imageName,HttpServletRequest req) throws MalformedURLException{

    	String imagePath = req.getServletContext().getRealPath("/upload/board/")+imageName;
        Path imagePathInFileSystem = Paths.get(imagePath);
        Resource resource = new UrlResource(imagePathInFileSystem.toUri());
        
        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    //게시판 등록
    @PostMapping("/user/boardInsert")
    public BoardDto boardInsert(
    		@RequestPart("writer") String boardWriter,
            @RequestPart("content") String boardContent,
            @RequestPart("boardImages") List<MultipartFile> boardImages,
            HttpServletRequest req
    ) throws IOException, ServletException {
    	
    	BoardDto dto = new BoardDto();
    	
		String token = null;
		Cookie[] cookies = req.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("User-Token".equals(cookie.getName())) {  // 쿠키의 이름이 "User-Token"인 경우
	                String cookieValue = cookie.getValue();
	                token = cookieValue;
	            }
	        }
	    }
	    Map username = JWTTokens.getTokenPayloads(token);
	    String id = (String)username.get("username");

        dto.setId(id);
        dto.setBoard_content(boardContent);
        dto.setBoard_writer(boardWriter);
        BoardDto returnDto =  service.boardInsert(dto,req);
        returnDto.setId(id);
        returnDto.setBoard_content(boardContent);
        returnDto.setBoard_writer(boardWriter);
        returnDto.setBoard_no(returnDto.getBoard_no());
        returnDto.setBoard_createtime(returnDto.getBoard_createtime());
        
    	return returnDto;
    }
    
    //댓글
    @GetMapping("/comment")
    public List<CommentDto> comment(@RequestParam String board_no) {
    	List<CommentDto> dto = service.commentList(board_no);
    	return dto;
    }
    
    //댓글 입력
    @PostMapping("/user/commentInsert")
    public void commentInsert(@RequestBody CommentDto commentDto,HttpServletRequest req) {
    	
		String token = null;
		Cookie[] cookies = req.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("User-Token".equals(cookie.getName())) {  // 쿠키의 이름이 "User-Token"인 경우
	                String cookieValue = cookie.getValue();
	                token = cookieValue;
	            }
	        }
	    }
	    Map username = JWTTokens.getTokenPayloads(token);
	    String id = (String)username.get("username");
    	
	    commentDto.setId(id);
    	
    	service.commentInsert(commentDto);
    }
    
    //좋아요 여부
    @GetMapping("/user/like")
    public boolean like(@RequestParam String board_no,HttpServletRequest req) {
		String token = null;
		Cookie[] cookies = req.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("User-Token".equals(cookie.getName())) {  // 쿠키의 이름이 "User-Token"인 경우
	                String cookieValue = cookie.getValue();
	                token = cookieValue;
	            }
	        }
	    }
	    Map username = JWTTokens.getTokenPayloads(token);
	    String id = (String)username.get("username");
	    
	    boolean islike = service.islike(id,board_no);
	    if(islike) return true;
	    else return false;
    }
    
    @GetMapping("/user/followList")
    public List<FollowDto> followList(HttpServletRequest req) throws IOException {
		String token = null;
		Cookie[] cookies = req.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("User-Token".equals(cookie.getName())) {  // 쿠키의 이름이 "User-Token"인 경우
	                String cookieValue = cookie.getValue();
	                token = cookieValue;
	            }
	        }
	    }
	    Map username = JWTTokens.getTokenPayloads(token);
	    String id = (String)username.get("username");
    	
	    List<FollowDto> followList = service.getFollowList(id);
    	if(followList.size() > 0) {
		    for(FollowDto value : followList) {
		    	if(value.getProfileImage().equals("0")) {
		    		value.setProfileImage("/img/dino.e5443f55.jpg");
		    	}
		    	else if(value.getProfileImage().startsWith("D:") || value.getProfileImage().startsWith("E:") ) {
		    	    String imagePath = value.getProfileImage();
		            Path imagePathInFileSystem = Paths.get(imagePath);
		            Resource resource = new UrlResource(imagePathInFileSystem.toUri());
		            byte[] imageBytes = resource.getInputStream().readAllBytes();
		            String base64Image = Base64.encodeBase64String(imageBytes);
		            value.setProfileImage("data:image/png;base64," + base64Image);
		    	}
		    	else {
		    		// 소셜일때는 가공필요없음
		    	}
		    }
    	}
    	
    	return followList;
    }
    
    @GetMapping("/user/chatList")
    public List<ChatDto> chatList(HttpServletRequest req,@RequestParam String rid){
		String token = null;
		Cookie[] cookies = req.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("User-Token".equals(cookie.getName())) {  // 쿠키의 이름이 "User-Token"인 경우
	                String cookieValue = cookie.getValue();
	                token = cookieValue;
	            }
	        }
	    }
	    Map username = JWTTokens.getTokenPayloads(token);
	    String id = (String)username.get("username");
    	String dm_no = service.getDmNo(id,rid);
    	List<ChatDto> chatList = service.chatList(dm_no);
    	for(ChatDto value : chatList) {
    		
    		if (id.compareTo(rid) < 0) {
    			value.setDm_chat_id(id + "-" + rid);
    		} 
    		else {
    			value.setDm_chat_id(rid + "-" + id);
    		}
    		
    		if(id.equalsIgnoreCase(value.getDm_sender_id())) {
    			value.setDm_type("me");
    		}
    		else {
    			value.setDm_type("you");
    		}
    	}
    	return chatList;
    }
    
    
    @GetMapping("/findById")
    public boolean findById(@RequestParam String id) {
    	return service.findById(id);
    }
    
    @PostMapping("/user/insertMessage")
    public void insertMessage(@RequestBody ChatDto chatdto) {
    	String id = chatdto.getDm_sender_id();
    	String rid = chatdto.getDm_recipient_id();
    	chatdto.setDm_no(service.getDmNo(id,rid));
    	service.insertMessage(chatdto);
    }
    
    @GetMapping("/noticeList")
    public List<NoticeDto> noticeList(HttpServletRequest req){
		String token = null;
		Cookie[] cookies = req.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("User-Token".equals(cookie.getName())) {  // 쿠키의 이름이 "User-Token"인 경우
	                String cookieValue = cookie.getValue();
	                token = cookieValue;
	            }
	        }
	    }
	    Map username = JWTTokens.getTokenPayloads(token);
	    String id = (String)username.get("username");
    	List<NoticeDto> noticeList = service.noticeList(id);
    	return noticeList;
    }
    
    @GetMapping("/successFollow")
    public String SuccessFollow(@RequestParam String notice_no) {
    	System.out.println(notice_no);
    	service.successFollow(notice_no);
    	return "success";
    	
    }
    @GetMapping("/failFollow")
    public String FailFollow(@RequestParam String notice_no) {
    	service.FailFollow(notice_no);
    	service.deleteNotice(notice_no);
    	return "fail";
    }

    @PostMapping("/insertReservation")
    public void insertReservation(@RequestBody ReservationDto reservationDto) {
    	System.out.println("1 : "+reservationDto.getId());
    	System.out.println("2 : "+reservationDto.getReservation_pricename());
    	System.out.println("3 : "+reservationDto.getReservation_receipturl());
    	System.out.println("4 : "+reservationDto.getReservation_lat());
    	System.out.println("5 : "+reservationDto.getReservation_lng());
    	System.out.println("6 : "+reservationDto.getReservation_price());
    	
    	service.insertReservation(reservationDto);
    }
    @GetMapping("/reservation")
    public List<ReservationDto> getReservation(@RequestParam String userId) {
        if (userId == null) {
            // 예외 처리 또는 다른 로직 추가
            return null;
        }
        List<ReservationDto> reservations = service.findReservationsByUserId(userId);
        System.out.println("wefwef"+reservations);
        return reservations;
    }
    
}
