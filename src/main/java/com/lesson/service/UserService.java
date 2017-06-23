package com.lesson.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lesson.models.User;
import com.lesson.utils.DataWrapper;

public interface UserService {
	DataWrapper<Void> register(User user, String code);
	
	DataWrapper<User> login(String phone, String password);
	
	DataWrapper<Void> forgetPwd(String phone, String code, String newPassword);
	
	DataWrapper<Void> changePwd(Long userId, String newPassword);
	
	DataWrapper<User> changeUserInfo(Long userId, User user);
	
	DataWrapper<User> getUserDetails(Long userId);
	
	DataWrapper<List<User>> getUserList(Integer numberPerPage, Integer currentPage);

	DataWrapper<Void> addNote(Long userId, String note);
	
	@Transactional
	DataWrapper<Void> deleteUser(Long userId);
}
