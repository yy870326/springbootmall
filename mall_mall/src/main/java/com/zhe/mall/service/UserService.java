package com.zhe.mall.service;

import com.zhe.mall.dto.UserLoginRequest;
import com.zhe.mall.dto.UserRegisterRequest;
import com.zhe.mall.model.User;

public interface UserService {
	
	Integer register(UserRegisterRequest userRegisterRequest);
	
	User getUserById(Integer id);
	

	User login(UserLoginRequest userLoginRequest);

}
