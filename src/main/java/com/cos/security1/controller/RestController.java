package com.cos.security1.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.cos.security1.service.MemberService;
import com.cos.security1.service.PlanService;
import com.cos.security1.service.dto.BoardDto;
import com.cos.security1.service.dto.ChatDto;
import com.cos.security1.service.dto.CommentDto;
import com.cos.security1.service.dto.FollowDto;
import com.cos.security1.service.dto.LocationDto;
import com.cos.security1.service.dto.NoticeDto;
import com.cos.security1.service.dto.PlanDto;
import com.cos.security1.service.dto.ReservationDto;
import com.cos.security1.service.dto.ScheduleDto;
import com.cos.security1.service.dto.UserDto;
import com.cos.security1.util.FileUtils;
import com.cos.security1.util.JWTTokens;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

@org.springframework.web.bind.annotation.RestController
public class RestController {
	
	@Autowired
	private MemberService service;	
	
	@Autowired
	private PlanService planService;
	@GetMapping("/user/test")
	public String userTest() {
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
        long size = profileImage.getSize();
        String filesize = Long.toString(size);
        String saveDirectory = req.getServletContext().getRealPath("/upload/profile/");
        System.out.println(saveDirectory);
		Collection<Part> parts = req.getParts();
		StringBuffer filenames = FileUtils.upload(parts, saveDirectory);
		UserDto dto = new UserDto();
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
    public List<BoardDto> board(
    		@RequestParam String num,
    		@RequestParam(required=false) String keyword,
    		HttpServletRequest request) throws IOException {
		
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
	    List<BoardDto> dto = service.boardList(num,request,keyword);
	    
	    if(id==null) //비회원일때 좋아요 조회 아이디없으면 guest로 진행
	    	id = "guest";
	    
    	for(BoardDto value : dto) {
    		boolean like = service.isBoardLike(id, value.getBoard_no());
    		if(like) {
    			value.setLike(true);
    		}
    	}
    	return dto;
    }
    
    @GetMapping("/myBoard")
    public List<BoardDto> myBoard(@RequestParam String id,HttpServletRequest request) throws IOException {
    	
    	List<BoardDto> dto = service.myBoard(id,request);
    			
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
    
    //게시판 등록
    @PostMapping(value = "/user/boardInsert", consumes = {"multipart/form-data"})
    public BoardDto handlePostRequest(
    		@RequestPart("boardImages") List<MultipartFile> boardImages,
            @RequestParam String title, 
            @RequestParam String content, 
            @RequestParam String name, 
            @RequestParam String profileimage, 
            @RequestParam(required = false) String tags,
            @RequestPart("dto") String dtoJson,
            HttpServletRequest req
    		) throws IOException, ServletException {
    	BoardDto dto = new BoardDto();
		String token = null;
		Cookie[] cookies = req.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("User-Token".equals(cookie.getName())) {
	                String cookieValue = cookie.getValue();
	                token = cookieValue;
	            }
	        }
	    }
	    Map username = JWTTokens.getTokenPayloads(token);
	    String id = (String)username.get("username");
    	
        ObjectMapper objectMapper = new ObjectMapper();
        LocationDto locationdto = objectMapper.readValue(dtoJson, LocationDto.class);
        
	    dto.setId(id);
	    dto.setBoard_writer(name);
	    dto.setBoard_title(title);
	    dto.setBoard_content(content);
	    dto.setBoard_tags(tags);
	    dto.setProfileimage(profileimage);
	    BoardDto returnDto =  service.boardInsert(dto,req,locationdto);
	    returnDto.setId(id);
	    returnDto.setBoard_writer(name);
	    returnDto.setBoard_title(title);
	    returnDto.setBoard_content(content);
	    returnDto.setBoard_tags(tags);
    	return returnDto;
    }
    
    //댓글
    @GetMapping("/comment")
    public List<CommentDto> comment(@RequestParam String board_no,HttpServletRequest req) throws IOException {
    	List<CommentDto> dto = service.commentList(board_no,req);
    	return dto;
    }
    
    //댓글 좋아요 입력
    @PostMapping("/commentLike")
    public void addCommentLike(@RequestBody Map<String, Object> payload) {
    	String comment_no = (String) payload.get("comment_no");
    	String id = (String) payload.get("id");
    	Boolean isLike = (Boolean) payload.get("isLike");
    	// 좋아요 추가할때
    	if(isLike)
    		service.addCommentLike(comment_no,id);
    	// 좋아요 취소할때
    	else
    		service.deleteCommentLike(comment_no,id);
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
    public List<NoticeDto> noticeList(HttpServletRequest req) throws IOException{
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
    	List<NoticeDto> noticeList = service.noticeList(id,req);
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
    
    @PostMapping("/getIsFollowList")
    public List<FollowDto> getIsFollowList(HttpServletRequest req) {
    	// 비회원일때 처리해야함.
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
	    
	    List<FollowDto> dto = service.getIsFollowList(id);
	    
	    return dto;
    }
    
    @PostMapping("/insertReservation")
    public void insertReservation(@RequestBody ReservationDto reservationDto) {
    	service.insertReservation(reservationDto);
    }
    
    @PostMapping("/insertFlightReservation")
    public void insertFlightReservation(@RequestBody ReservationDto reservationDto) {
    	service.insertFlightReservation(reservationDto);
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
    
    @GetMapping("/flightReservation")
    public List<ReservationDto> getFlightReservation(@RequestParam String userId) {
        if (userId == null) {
            // 예외 처리 또는 다른 로직 추가
            return null;
        }
        List<ReservationDto> flightReservations = service.findFlightReservationsByUserId(userId);
        
        return flightReservations;
    }
    
    @GetMapping("/getMypageHeader")
    public UserDto getMypageHeader(@RequestParam String id,HttpServletRequest request) throws IOException {
    	UserDto header = service.getMypageHeader(id,request);
    	return header;
    }
    
    @GetMapping("/getMyFollowList")
    public List<FollowDto> getMyFollowList(@RequestParam String id,HttpServletRequest request) throws IOException {
    	List<FollowDto> list = service.getMyFollowList(id,request);
    	return list;
    }
    
    @PostMapping("/addSchedule")
    public void insertSchedule(@RequestBody ScheduleDto scheduleDto) {
    	planService.addSchedule(scheduleDto);
    }
    
    @PostMapping("/addPlan")
    public void insertPlan(@RequestBody PlanDto planDto) {
    	System.out.println(planDto);
    	planService.addPlan(planDto);
    }
    
    @GetMapping("/getSchedule")
    public List<ScheduleDto> findSchedule(@RequestParam String id){
    	return planService.findSchedules(id);
    }
    
    @GetMapping("/getScheduleByNo")
    public ScheduleDto findScheduleByNo(@RequestParam String schedule_no) {
    	return planService.findScheduleByNo(schedule_no);
    }
    
    @GetMapping("/getPlan")
    public List<PlanDto> findPlan(@RequestParam Long schedule_no){
    	return planService.findPlans(schedule_no);
    }
    
    
}
