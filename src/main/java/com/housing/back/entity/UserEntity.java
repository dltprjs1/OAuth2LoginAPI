package com.housing.back.entity;

import lombok.Getter;

import com.housing.back.dto.request.auth.SignUpRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="user")
@Table(name="userT")
public class UserEntity {
	@Id
	private String userId;
	private String password;
	private String email;
	private String type;
	private String role;
	
	public UserEntity(SignUpRequestDto dto) {
		this.userId = dto.getId();
		this.password = dto.getPassword();
		this.email = dto.getEmail();
		this.type = "app";
		this.role = "ROLE_USER";
	}
	
	public UserEntity(String usrId,String email, String type) {
		this.userId = usrId;
		this.password = "Passw0rdse";
		this.email = email;
		this.type = type;
		this.role = "ROLE_USER";
	}
	
}
