package com.cos.security1.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.cos.security1.service.MemberService;
import com.cos.security1.service.dto.ChatDto;
import com.cos.security1.service.dto.FollowDto;
import com.cos.security1.service.dto.NoticeDto;

@Controller
public class ChatController {
	
	@Autowired
	private MemberService service;	
	
	@Autowired
	private SimpMessagingTemplate template;
	
	// 1:1 채팅 웹소켓
	@MessageMapping("/chat.{chatId}sendMessage")
	@SendTo("/topic/{chatId}")
    public ChatDto sendMessage(@Payload ChatDto message) {
    	System.out.println("sendMessage 통신 message : "+message.getDm_content());
        return message;
    }
	
	@MessageMapping("/followRequest")
	public void processFollowRequest(@Payload FollowDto followRequest) {
		// 아이디 같을 경우 클라이언트에서해야함..
	    if (followRequest.getFollow_senderid().equals(followRequest.getFollow_recipientid())) {
	    	followRequest.setFollow_isRequest("id_same");
	    }
	    // 이미 요청 한 경우 레스트컨트롤러로 옮기자..살려줘
	    boolean alreadyRequested = service.checkFollowRequest(followRequest);
	    if (alreadyRequested) {
	        followRequest.setFollow_isRequest("already_request");
	    }
		sendNotification(followRequest);
	}
	
	public void sendNotification(FollowDto followRequest) {
		//처음 요청인 경우
		if(followRequest.getFollow_isRequest()==null) {

			// 알림테이블 추가
		    NoticeDto dto = new NoticeDto();
		    dto.setNotice_content("팔로우 요청을 보냈습니다.");
		    dto.setId(followRequest.getFollow_recipientid());
			service.insertNotice(dto);
			
			// notice_no를 가져오자
			String notice_no = service.getNoticeNo(followRequest);
			followRequest.setNotice_no(notice_no);
			// 팔로우테이블 추가
			service.insertFollow(followRequest);
			
			Date now = new Date();
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String formattedDate = formatter.format(now);
			followRequest.setFollow_requesttime(formattedDate);
			System.out.println(formattedDate);
			followRequest.setFollow_isRequest("ok");
			System.out.println("오는거야 ?");
			System.out.println(followRequest.getProfileImage());
			
		}
			
		template.convertAndSendToUser(followRequest.getFollow_recipientid(), "/queue/notification", followRequest);
	}
	
}
