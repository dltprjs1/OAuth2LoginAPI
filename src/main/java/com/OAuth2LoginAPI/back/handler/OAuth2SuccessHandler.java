package com.OAuth2LoginAPI.back.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.OAuth2LoginAPI.back.entity.CustomOAuth2User;
import com.OAuth2LoginAPI.back.provider.JwtProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	private final JwtProvider jwtProvider;
	
	public void onAuthenticationSuccess(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Authentication authentication
	) throws IOException, ServletException {
		
		CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
		
		String userId = oAuth2User.getName();
		String token = jwtProvider.create(userId);		//userId를 받아와 해당 Id의 token을 생성 해주는 메서드
		
		response.sendRedirect("http://localhost:3000/auth/oauth-response/" + token + "/3600");	//20240625_LSG DNS,SSL 작업 후 수정 필요
		
	}
}
