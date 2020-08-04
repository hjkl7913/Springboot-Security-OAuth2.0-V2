package com.cos.securityex01.config.auth.oauth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.securityex01.config.auth.PrincipalDetails;
import com.cos.securityex01.config.auth.oauth.provide.FacebookUserInfo;
import com.cos.securityex01.config.auth.oauth.provide.GoogleUserInfo;
import com.cos.securityex01.config.auth.oauth.provide.OAuth2UserInfo;
import com.cos.securityex01.model.User;
import com.cos.securityex01.repository.UserRepository;


@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	// userRequest 는 code 를 받아서 accessToken을 응답 받은 객체
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		//코드,토큰,회원정보를 다받아서 oAuth2User 넣어준다. -> 기존의 PrincipalDetails 에 넣어주면 됨
		OAuth2User oAuth2User = super.loadUser(userRequest); //google 의 회원 프로필 
		
		// oAuth2User 정보를 어디에 담아서 무엇을 리턴하면 될까요?
		// 1번 : PrincipalDetails 에 OAuth2User 정보를 집어 넣어 준다.
		// 2번 : PrincipalDetails를 리턴한다.
		
		System.out.println("userRequser : "+userRequest); // userRequest 토큰까지 받은 오브젝트
		System.out.println("userRequser token : "+userRequest.getAccessToken().getTokenValue()); // userRequest 토큰까지 받은 오브젝트
		System.out.println("userRequser  Registration : "+userRequest.getClientRegistration()); // userRequest 메모리정보
		System.out.println("oAuth2User : "+oAuth2User); //토큰을 통해 응답받은 회원정보
		
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return processOAuth2User(userRequest, oAuth2User);
	}
	
	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
		
		// 일반적으로는 로그인 할때 유저 정보 User
		// 1. OAuth2 로 로그인할 때 유저 정보 attributes <- 이거 구성해야함
		OAuth2UserInfo oAuth2UserInfo = null;
		if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
		}else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
			oAuth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());
		} else {
			System.out.println("우리는 구글과 페이스북만 지원해요 ㅎㅎ");
		}
		
		
		Optional<User> userOptional = userRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());
		System.out.println("userOptional : "+userOptional);
		// 2. DB에 이사람있나?
		
		// 있으면?
		
		// --> 있으면 update (구글에서 정보가 바뀌었을수도 있으니까)해야함
		
		// 없으면?
		
		// --> 없으면 insert 해야함
		
		User user;
		
		if(userOptional.isPresent()) {
			user = userOptional.get();
			// user가 존재하면 update 해주기
			user.setEmail(oAuth2UserInfo.getEmail());
			userRepository.save(user);
		} else {
			// user의 패스워드가 null이기 때문에 OAuth 유저는 일반적인 로그인을 할 수 없음.
			user = User.builder()
					.username(oAuth2UserInfo.getProvider() + "-" + oAuth2UserInfo.getProviderId())
					.email(oAuth2UserInfo.getEmail())
					.role("ROLE_USER")
					.provider(oAuth2UserInfo.getProvider())
					.providerId(oAuth2UserInfo.getProviderId())
					.build();
			userRepository.save(user);
		}
		
		return new PrincipalDetails(user, oAuth2User.getAttributes());
		//return oAuth2User;
	}
}
