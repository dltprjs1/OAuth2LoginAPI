package com.housing.back.service.implement;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.housing.back.entity.CustomOAuth2User;
import com.housing.back.entity.UserEntity;
import com.housing.back.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImplement extends DefaultOAuth2UserService {

	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {

		OAuth2User oAuth2User = super.loadUser(request);
		
		/* 소셜 네트워크 서버에서 보내 주는 UserInfo 데이터 확인
		 * try {
		 * 
		 * System.out.println(new
		 * ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
		 * 
		 * }catch(Exception exception) { exception.printStackTrace(); }
		 */
		
		String oauthClientName = request.getClientRegistration().getClientName();			
		UserEntity userEntity = null;
		String userId = null;
		String email = "email@email.com";	// 카카오의 경우 이메일이 필수 동의 목록에 제외되어 데이터 베이스에 저장 시 해당 메일로 저장


		if (oauthClientName.equals("kakao")) {
			userId = "kakao_" + oAuth2User.getAttributes().get("id");
			userEntity = new UserEntity(userId, email, "kakao");
		}

		if (oauthClientName.equals("naver")) {
			Map<String, String> responseMap = (Map<String, String>) oAuth2User.getAttributes().get("response");
			userId = "naver_" + responseMap.get("id").substring(0,14);
			email = responseMap.get("email");
			userEntity = new UserEntity(userId, email, "naver");
		}

		userRepository.save(userEntity);

		return new CustomOAuth2User(userId);
	}

}