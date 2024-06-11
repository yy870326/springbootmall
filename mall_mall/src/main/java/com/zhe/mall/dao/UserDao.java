package com.zhe.mall.dao;

import com.zhe.mall.dto.UserLoginRequest;
import com.zhe.mall.dto.UserRegisterRequest;
import com.zhe.mall.model.User;

public interface UserDao {
	Integer createUser(UserRegisterRequest userRegisterRequest);
	
	User getUserById(Integer id);
	User getUserByEmail(String email);

}
