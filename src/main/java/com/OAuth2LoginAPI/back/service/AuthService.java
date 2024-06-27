package com.OAuth2LoginAPI.back.service;

import org.springframework.http.ResponseEntity;

import com.OAuth2LoginAPI.back.dto.request.auth.CheckCertificationRequestDto;
import com.OAuth2LoginAPI.back.dto.request.auth.EmailCertificationRequestDto;
import com.OAuth2LoginAPI.back.dto.request.auth.IdCheckRequestDto;
import com.OAuth2LoginAPI.back.dto.request.auth.SignInRequestDto;
import com.OAuth2LoginAPI.back.dto.request.auth.SignUpRequestDto;
import com.OAuth2LoginAPI.back.dto.response.auth.CheckCertificationResponseDto;
import com.OAuth2LoginAPI.back.dto.response.auth.EmailCertificationResponseDto;
import com.OAuth2LoginAPI.back.dto.response.auth.IdCheckResponseDto;
import com.OAuth2LoginAPI.back.dto.response.auth.SignInResponseDto;
import com.OAuth2LoginAPI.back.dto.response.auth.SignUpResponseDto;

public interface AuthService {

	ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto dto);
	ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto);
	ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto);
	ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);
	ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);
}
