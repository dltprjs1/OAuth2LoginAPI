package com.OAuth2LoginAPI.back.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.OAuth2LoginAPI.back.common.ResponseCode;
import com.OAuth2LoginAPI.back.common.ResponseMessage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto {
	private String code;
	private String message;
	
	public ResponseDto() {
		this.code = ResponseCode.SUCCESS;
		this.message = ResponseMessage.SUCCESS;
	}
	
	public static ResponseEntity<ResponseDto> databaseError(){
		ResponseDto responseBody = new ResponseDto(ResponseCode.DATABASE_ERROR, ResponseMessage.DATABASE_ERROR);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
	}
	
	public static ResponseEntity<ResponseDto> validationFail(){
		ResponseDto responseBody = new ResponseDto(ResponseCode.DATABASE_ERROR, ResponseMessage.DATABASE_ERROR);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	}
}
