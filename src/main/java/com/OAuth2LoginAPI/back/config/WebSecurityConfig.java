package com.OAuth2LoginAPI.back.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.OAuth2LoginAPI.back.filter.JwtAuthenticationFilter;
import com.OAuth2LoginAPI.back.handler.OAuth2SuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configurable
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final DefaultOAuth2UserService oAuth2UserService;
	private final OAuth2SuccessHandler oAuth2SuccessHandler;
	
	@Bean
	protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception{
		httpSecurity
			.cors(cors -> cors
				.configurationSource(corsConfigurationSource())
			)
			.csrf(CsrfConfigurer::disable)
			.httpBasic(HttpBasicConfigurer::disable)
			.sessionManagement(sessionManagement -> sessionManagement
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.authorizeHttpRequests(request -> request
					.requestMatchers("/", "/api/v1/auth/**", "/oauth2/**").permitAll()				// 로그인 시 api 로그인 url Custom
					.requestMatchers("/api/v1/user/**").hasRole("USER")
					.requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
					.anyRequest().authenticated()
			)
			.oauth2Login(oauth2 -> oauth2
					.authorizationEndpoint(endpoint -> endpoint.baseUri("/api/v1/auth/oauth2"))		// Client가 요청 보낼 url Custom 가능 ex) localhost:4040/api/v1/auth/oauth2/kakao << 카카오 로그인
					.redirectionEndpoint(endpoint -> endpoint.baseUri("/oauth2/callback/*"))		// 소셜 네트워크(네이버, 카카오 등)의 서버가 보내주는 response(authorization_code, satus)의 url
					.userInfoEndpoint(endpoint -> endpoint.userService(oAuth2UserService))			// 소셜 네트워크가 code와 status를 받아 userInfo를 response 해주는 인스턴스(oAuth2userService ==> extends DefaultOauth2UserService ==> loadUser 메서드 오버라이드)
					.successHandler(oAuth2SuccessHandler)											// 소셜 네트워크 로그인 성공 시 사용자에게 보여질 url 인스턴스
			)																				
			.exceptionHandling(exceptionHandling -> exceptionHandling
					.authenticationEntryPoint(new FailedAuthenticationEntryPoint())
			)
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return httpSecurity.build();
		
	}
	
	@Bean
	protected CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.addAllowedMethod("*");
		corsConfiguration.addAllowedHeader("*");
		
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);
		
		return source;
		
	}
	
}

class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.getWriter().write("{\"code\": \"NP\", \"message\": \"No Permission.\"}");
		
	}
	
}