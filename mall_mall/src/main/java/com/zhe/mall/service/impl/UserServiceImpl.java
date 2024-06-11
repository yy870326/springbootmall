package com.zhe.mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.zhe.mall.dao.UserDao;
import com.zhe.mall.dto.UserLoginRequest;
import com.zhe.mall.dto.UserRegisterRequest;
import com.zhe.mall.model.User;
import com.zhe.mall.service.UserService;

@Component
public class UserServiceImpl implements UserService {

	private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Override
	public Integer register(UserRegisterRequest userRegisterRequest) {
		// 檢查
		User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

		if (user != null) {
			log.warn("該 email {} 已經被註冊", userRegisterRequest.getEmail());
			// {}是變數
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		// 使用 MD5 生成密碼的雜湊值
		String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
		userRegisterRequest.setPassword(hashedPassword);

		// 創建帳號
		return userDao.createUser(userRegisterRequest);
	}

	@Override
	public User getUserById(Integer id) {
		User user = userDao.getUserById(id);
		return user;
	}

	@Override
	public User login(UserLoginRequest userLoginRequest) {

		// 檢查
		User user = userDao.getUserByEmail(userLoginRequest.getEmail());

		if (user == null) {
			log.warn("該 email {} 未被註冊", userLoginRequest.getEmail());
			// {}是變數
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		// 使用 MD5 生成密碼的雜湊值
		String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());

		// 比較密碼
		if (user.getPassword().equals(hashedPassword)) {
			return user;
		} else {
			log.warn("email {} 的密碼不正確", userLoginRequest.getEmail());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}

}
