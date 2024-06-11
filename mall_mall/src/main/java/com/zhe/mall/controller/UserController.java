package com.zhe.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zhe.mall.dto.UserLoginRequest;
import com.zhe.mall.dto.UserRegisterRequest;
import com.zhe.mall.model.User;
import com.zhe.mall.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/users/register") 
	public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
		Integer id = userService.register(userRegisterRequest);
		
		User user = userService.getUserById(id);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
	
	@PostMapping("/users/login")
	public ResponseEntity<User> login(@RequestBody UserLoginRequest userLoginRequest){
		User user = userService.login(userLoginRequest);
		
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
}
