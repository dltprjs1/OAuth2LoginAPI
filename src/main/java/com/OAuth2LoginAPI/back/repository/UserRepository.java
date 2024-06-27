package com.OAuth2LoginAPI.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.OAuth2LoginAPI.back.entity.UserEntity;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>{

	boolean existsByUserId(String UserId);
	
	UserEntity findByUserId(String userId);
	
}
