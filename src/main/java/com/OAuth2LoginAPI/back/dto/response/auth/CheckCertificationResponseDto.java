package com.OAuth2LoginAPI.back.dto.response.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.OAuth2LoginAPI.back.common.ResponseCode;
import com.OAuth2LoginAPI.back.common.ResponseMessage;
import com.OAuth2LoginAPI.back.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class CheckCertificationResponseDto extends ResponseDto{

	private CheckCertificationResponseDto () {
		super();
	}
	
	public static ResponseEntity<CheckCertificationResponseDto> success () {
		CheckCertificationResponseDto responseBody = new CheckCertificationResponseDto();
		return ResponseEntity.status(HttpStatus.OK).body(responseBody);
	}
	
	public static ResponseEntity<ResponseDto> certificationFail () {
		ResponseDto responseBody = new ResponseDto(ResponseCode.CERTIFICATION_FAIL, ResponseMessage.CERTIFICATION_FAIL);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
	}
	
}
