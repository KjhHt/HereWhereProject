package com.cos.security1.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cos.security1.service.dto.BoardDto;
import com.cos.security1.service.dto.CommentDto;
import com.cos.security1.service.dto.UserDto;
import com.cos.security1.util.FileUtils;

import jakarta.servlet.ServletException;
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
		System.out.println("joinMember 완료");
		// member_info 테이블
		mapper.joinMemberInfo(dto);
		System.out.println("joinMemberInfo 완료");
		// profileimage_table 테이블
		mapper.profileImageTable(dto);
		System.out.println("profileImageTable 완료");
	}

	public void updateRecentLoginTime(String id) {
		mapper.updateRecentLoginTime(id);
	}

	public List<BoardDto> boardList(String num) {
		return mapper.boardList(num);
	}

	public BoardDto boardInsert(BoardDto dto, HttpServletRequest req) throws IOException, ServletException {
		
		String saveDirectory = req.getServletContext().getRealPath("/upload/board/");
		
		Collection<Part> parts = req.getParts();
		StringBuffer filenames = FileUtils.upload(parts, saveDirectory);
		
		dto.setBoard_imageFileName(filenames.toString());
		
		mapper.boardInsert(dto);
		String fileName = filenames.toString();
		BoardDto returnDto = mapper.findBoardNo(fileName);
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
		
		return returnDto;
		
	}

	public List<CommentDto> commentList(String board_no) {
		return mapper.commentList(board_no);
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



    

	
}
