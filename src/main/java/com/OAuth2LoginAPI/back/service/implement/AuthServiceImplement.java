package com.OAuth2LoginAPI.back.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.OAuth2LoginAPI.back.common.CertificationNumber;
import com.OAuth2LoginAPI.back.dto.request.auth.CheckCertificationRequestDto;
import com.OAuth2LoginAPI.back.dto.request.auth.EmailCertificationRequestDto;
import com.OAuth2LoginAPI.back.dto.request.auth.IdCheckRequestDto;
import com.OAuth2LoginAPI.back.dto.request.auth.SignInRequestDto;
import com.OAuth2LoginAPI.back.dto.request.auth.SignUpRequestDto;
import com.OAuth2LoginAPI.back.dto.response.ResponseDto;
import com.OAuth2LoginAPI.back.dto.response.auth.CheckCertificationResponseDto;
import com.OAuth2LoginAPI.back.dto.response.auth.EmailCertificationResponseDto;
import com.OAuth2LoginAPI.back.dto.response.auth.IdCheckResponseDto;
import com.OAuth2LoginAPI.back.dto.response.auth.SignInResponseDto;
import com.OAuth2LoginAPI.back.dto.response.auth.SignUpResponseDto;
import com.OAuth2LoginAPI.back.entity.CertificationEntity;
import com.OAuth2LoginAPI.back.entity.UserEntity;
import com.OAuth2LoginAPI.back.provider.EmailProvider;
import com.OAuth2LoginAPI.back.provider.JwtProvider;
import com.OAuth2LoginAPI.back.repository.CertificationRepository;
import com.OAuth2LoginAPI.back.repository.UserRepository;
import com.OAuth2LoginAPI.back.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

	private final UserRepository userRepository;
	
	private final CertificationRepository certificationRepository;
	
	private final JwtProvider jwtProvider;
	
	private final EmailProvider emailProvider;
	
	private PasswordEncoder  passwordEncoder= new BCryptPasswordEncoder();
	
	
	@Override
	public ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto dto) {
		
		try {
			
			String userId = dto.getId();
			
			boolean isExistId = userRepository.existsByUserId(userId);
			if (isExistId) return IdCheckResponseDto.duplicateId();
			
		}catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		return IdCheckResponseDto.success();
	}

	@Override
	public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto) {
		
		try {
			
			String userId = dto.getId();
			String email = dto.getEmail();
			
			boolean isExistId = userRepository.existsByUserId(userId);
			if (isExistId) return EmailCertificationResponseDto.duplicateId();
			
			String certificationNumber = CertificationNumber.getCertificationNumber();
			
			boolean isSuccessed = emailProvider.sendCertificationMail(email, certificationNumber);
			if(!isSuccessed) return EmailCertificationResponseDto.mailSendFail();
			
			
			CertificationEntity certificationEntity = new CertificationEntity(userId, email, certificationNumber);
			certificationRepository.save(certificationEntity);
			
		}catch(Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		return EmailCertificationResponseDto.success();
	}

	@Override
	public ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto) {
		
		try {
			
			String userId = dto.getId();
		 	String email = dto.getEmail();
			String certificationNumber =  dto.getCertificationNumber();
			
			CertificationEntity certificationEntity = certificationRepository.findByUserId(userId);
			if (certificationEntity == null) return CheckCertificationResponseDto.certificationFail();
			
			boolean isMatched = certificationEntity.getEmail().equals(email) && certificationEntity.getCertificationNumber().equals(certificationNumber);
			
			if (!isMatched) return CheckCertificationResponseDto.certificationFail();
			
		}catch(Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		return CheckCertificationResponseDto.success();
	}

	@Override
	public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {
		
		try {
			String userId = dto.getId();
			
			boolean isExisted = userRepository.existsByUserId(userId);
			if (isExisted) return SignUpResponseDto.duplicateId();
			
			String email = dto.getEmail();
			String certificationNumber = dto.getCertificationNumber();
			
			CertificationEntity certificationEntity = certificationRepository.findByUserId(userId);
			boolean isMathed = certificationEntity.getEmail().equals(email) && certificationEntity.getCertificationNumber().equals(certificationNumber);
			
			if (!isMathed) return SignUpResponseDto.certificationFail();

			String password = dto.getPassword();
			String edcodedPassword = passwordEncoder.encode(password);
			dto.setPassword(edcodedPassword);
			
			UserEntity userEntity = new UserEntity(dto);
			userRepository.save(userEntity);

			certificationRepository.deleteByUserId(userId);
			
		}catch(Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		return SignUpResponseDto.success();
	}

	@Override
	public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {
		
		String token = null;
		
		try {
			
			String userId = dto.getId();
			UserEntity userEntity = userRepository.findByUserId(userId);
			if (userEntity == null) return SignInResponseDto.signInFail();
			
			String password = dto.getPassword();
			String encodePassword = userEntity.getPassword();
			boolean isMatched = passwordEncoder.matches(password, encodePassword);
			if (!isMatched) return SignInResponseDto.signInFail();
			
			token = jwtProvider.create(userId);
			
		}catch(Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		return SignInResponseDto.success(token);
	}


}
