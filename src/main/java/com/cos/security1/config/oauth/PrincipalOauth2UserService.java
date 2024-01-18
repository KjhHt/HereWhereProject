package com.cos.security1.config.oauth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.service.MemberService;
import com.cos.security1.service.dto.UserDto;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{

	@Autowired
	private MemberService service;
	// 구글로 받은 userRequest 후 처리되는 함수
	// 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		System.out.println("getClientRegistration : "+userRequest.getClientRegistration());
		System.out.println("getAccessToken : "+userRequest.getAccessToken().getTokenValue());
		
		OAuth2User oauth2User = super.loadUser(userRequest);
		
		
		UserDto userEntity = new UserDto();
		boolean isUser = false;
		
		String provider = userRequest.getClientRegistration().getRegistrationId(); 
		System.out.println("loadUser provider : "+provider);
		if(provider.equals("google")) {
			
			String id= oauth2User.getAttribute("sub");
			String profileimage = oauth2User.getAttribute("picture");
			String name = oauth2User.getAttribute("name");
			
			userEntity.setId(id);
			userEntity.setName(name);
			userEntity.setProfileimage(profileimage);
			userEntity.setAuthority("ROLE_USER");
			userEntity.setProvider(provider);
			
			isUser = service.isPreviousUser(id);
			
			
		}
		else if(provider.equals("facebook")) {
			String id= oauth2User.getAttribute("id");
			String profileimage = "0";
			String name = oauth2User.getAttribute("name");
			
			userEntity.setId(id);
			userEntity.setName(name);
			userEntity.setProfileimage(profileimage);
			userEntity.setAuthority("ROLE_USER");
			userEntity.setProvider(provider);
			
			isUser = service.isPreviousUser(id);
		}
		else if(provider.equals("kakao")) {
			
			Long longid= (Long)oauth2User.getAttributes().get("id");
			String id = longid.toString();
			String profileimage = (String)((Map)oauth2User.getAttributes().get("properties")).get("profile_image");
			String name = (String)((Map)oauth2User.getAttributes().get("properties")).get("nickname");
			
			userEntity.setId(id);
			userEntity.setName(name);
			userEntity.setProfileimage(profileimage);
			userEntity.setAuthority("ROLE_USER");
			userEntity.setProvider(provider);
			
			isUser = service.isPreviousUser(id);
		}
		else if(provider.equals("naver")) {
			Map<String, Object> attributes = oauth2User.getAttributes();
			Map<String, Object> response = (Map<String, Object>) attributes.get("response");
			
			String id = (String) response.get("id");
			String profileimage = (String) response.get("profile_image");
			//response.get("nickname") <= 사용자이름임 참고 지금은 닉네임으로
			String name = (String) response.get("nickname");
			
			userEntity.setId(id);
			userEntity.setName(name);
			userEntity.setProfileimage(profileimage);
			userEntity.setAuthority("ROLE_USER");
			userEntity.setProvider(provider);
			
			isUser = service.isPreviousUser(id);
		}
		
		if(!isUser) {
			service.joinSocialMember(userEntity);
		}
		else {
			System.out.println("이미 로그인했던 회원");
		}
		
		return new PrincipalDetails(userEntity,oauth2User.getAttributes());
	}
	
}
