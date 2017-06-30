package com.lesson.service.impl;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lesson.enums.UserType;
import com.lesson.exceptions.AuthException;
import com.lesson.exceptions.CodeException;
import com.lesson.exceptions.DBException;
import com.lesson.exceptions.ParameterException;
import com.lesson.models.Token;
import com.lesson.models.User;
import com.lesson.repository.BookRepository;
import com.lesson.repository.CourseCodeRepository;
import com.lesson.repository.TokenRepository;
import com.lesson.repository.UserRepository;
import com.lesson.service.UserService;
import com.lesson.utils.DataWrapper;
import com.lesson.utils.MD5Util;
import com.lesson.utils.UUIDGenerator;
import com.lesson.utils.VerifyCodeManager;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TokenRepository tokenRepository;
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	CourseCodeRepository courseCodeRepository;

	@Override
	public DataWrapper<Void> register(User user, String code) {
		// TODO Auto-generated method stub
		if (user.getPhone() == null || user.getPhone().length() != 11) {
			throw new ParameterException("手机号码不正确");
		}
		if (user.getPassword() == null || user.getPassword().equals("")) {
			throw new ParameterException("密码为空");
		}
		if (user.getNickName() == null || user.getNickName().equals("")) {
			throw new ParameterException("宝宝昵称为空");
		}
		if (user.getGender() == null || !(user.getGender().equals("F") || user.getGender().equals("M")) ) {
			throw new ParameterException("性别错误");
		}
		if (user.getBirthday() == null) {
			throw new ParameterException("生日为空");
		}
		if (user.getCity() == null || user.getCity().equals("")) {
			throw new ParameterException("城市为空");
		}
		if (user.getParent() == null || user.getParent().equals("")) {
			throw new ParameterException("身份为空");
		}
        User userExisted = userRepository.findByPhone(user.getPhone());
        if (userExisted != null) {
        	throw new ParameterException("手机已被注册");
		}
        
        String serverCode = VerifyCodeManager.getPhoneCode(user.getPhone());
        if(serverCode.equals("noCode")) {
        	throw new CodeException("验证码不存在");
        } else if(serverCode.equals("overdue")) {
        	throw new CodeException("验证码超过2分钟");
        } else if(serverCode.equals(code)) {
        	user.setPassword(MD5Util.getMD5String(user.getPassword()));
        	user.setUserId(null);
        	user.setNote(null);
        	user.setType(UserType.User.getCode());
        	user.setRegisterDate(new Date());
        	try {
				userRepository.save(user);
				VerifyCodeManager.removePhoneCodeByPhoneNum(user.getPhone());
			} catch (Exception e) {
				throw new DBException("数据库错误",e);
			}
        } else {
        	throw new CodeException("验证码错误");
        }
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        return dataWrapper;
	}

	@Override
	public DataWrapper<User> login(String phone, String password) {
		// TODO Auto-generated method stub
		User user = userRepository.findByPhone(phone);
		if (user == null) {
			throw new AuthException("用户未注册");
		}
		
		if (!user.getPassword().equals(MD5Util.getMD5String(password))) {
			throw new AuthException("密码错误");
		}
		Token token = tokenRepository.findByUserId(user.getUserId());
		if (token == null) {
			token = new Token();
			token.setId(null);
			token.setLoginDate(new Timestamp(System.currentTimeMillis()));
			token.setTokenStr(UUIDGenerator.getCode());
			token.setUserId(user.getUserId());
			token.setUserType(user.getType());
			try {
				tokenRepository.save(token);
			} catch (Exception e) {
				throw new DBException("数据库错误",e);
			}
		
		} else {
			token.setLoginDate(new Timestamp(System.currentTimeMillis()));
			token.setTokenStr(UUIDGenerator.getCode());
			token.setUserType(user.getType());
			try {
				tokenRepository.save(token);
			} catch (Exception e) {
				throw new DBException("数据库错误",e);
			}
		}
		user.setPassword(null);
		DataWrapper<User> dataWrapper = new DataWrapper<User>();
		dataWrapper.setData(user);
        dataWrapper.setToken(token.getTokenStr());
        return dataWrapper;
	}

	@Override
	public DataWrapper<Void> forgetPwd(String phone, String code, String newPassword) {
		String serverCode = VerifyCodeManager.getPhoneCode(phone);
		if(serverCode.equals("noCode")){
			throw new CodeException("验证码不存在");
	    } else if(serverCode.equals("overdue")){
	    	throw new CodeException("验证码超过2分钟");
	    } else if(serverCode.equals(code)) {
	    	User user = userRepository.findByPhone(phone);
	    	if (user == null) {
	    		throw new AuthException("用户不存在");
			}
	    	try {
	    		userRepository.updatePasswordByUserId(MD5Util.getMD5String(newPassword), user.getUserId());
			} catch (Exception e) {
				throw new DBException("数据库错误",e);
				
			}
	    	VerifyCodeManager.removePhoneCodeByPhoneNum(phone);
	    } else {
        	throw new CodeException("验证码错误");
        }
		DataWrapper<Void> dataWrapper = new DataWrapper<>();
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> changePwd(Long userId, String newPassword) {
		// TODO Auto-generated method stub
		User user = userRepository.findByUserId(userId);
		if (user == null) {
			throw new AuthException("用户不存在");
		}
		try {
			userRepository.updatePasswordByUserId(MD5Util.getMD5String(newPassword), user.getUserId());
		} catch (Exception e) {
			throw new DBException("数据库错误",e);
		}
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		return dataWrapper;
	}

	@Override
	public DataWrapper<User> changeUserInfo(Long userId, User user) {
		// TODO Auto-generated method stub
		User userInDB = userRepository.findByUserId(userId);
		if (userInDB == null) {
			throw new AuthException("用户不存在");
		}
		
		if (user.getNickName() != null && !user.getNickName().equals("")) {
			userInDB.setNickName(user.getNickName());
		}
		if (user.getGender() != null && user.getGender().length() == 1) {
			userInDB.setGender(user.getGender());
		}
		if (user.getBirthday() != null) {
			userInDB.setBirthday(user.getBirthday());
		}
		if (user.getCity() != null && !user.getCity().equals("")) {
			userInDB.setCity(user.getCity());
		}
		
		if (user.getHeadSrc() != null && !user.getHeadSrc().equals("")) {
			userInDB.setHeadSrc(user.getHeadSrc());
		}
		if (user.getParent() != null && !user.getParent().equals("")) {
			userInDB.setParent(user.getParent());
		}
		
		try {
			userRepository.save(userInDB);
		} catch (Exception e) {
			throw new DBException("数据库错误",e);
		}
		DataWrapper<User> dataWrapper = new DataWrapper<User>();
		userInDB.setPassword(null);
		dataWrapper.setData(userInDB);
		return dataWrapper;
	}

	@Override
	public DataWrapper<User> getUserDetails(Long userId) {
		// TODO Auto-generated method stub
		User user = userRepository.findByUserId(userId);
		user.setPassword(null);
		DataWrapper<User> dataWrapper = new DataWrapper<User>();
		dataWrapper.setData(user);
		return dataWrapper;
	}

	@Override
	public DataWrapper<List<User>> getUserList(Integer numberPerPage, Integer currentPage) {
		// TODO Auto-generated method stub
		Pageable pageable = null;
		if (numberPerPage == null || numberPerPage <= 0 || numberPerPage > 10) {
			numberPerPage = 10;
		}
		if (currentPage == null || currentPage <= 0) {
			currentPage = 1;
		}
		
		pageable = new PageRequest(currentPage-1, numberPerPage);
		
		Page<User> page = userRepository.findUserListByType(UserType.User.getCode(), pageable);
		for(User eUser : page.getContent()) {
			eUser.setPassword(null);
			eUser.setReadCount(bookRepository.countByUserId(eUser.getUserId()));
			eUser.setCourseCount(courseCodeRepository.countByUserId(eUser.getUserId()));
		}
		
		DataWrapper<List<User>> dataWrapper = new DataWrapper<List<User>>();
		dataWrapper.setData(page.getContent());
        dataWrapper.setTotalNumber((int)page.getTotalElements());
        dataWrapper.setCurrentPage(currentPage);
        dataWrapper.setTotalPage(page.getTotalPages());
        dataWrapper.setNumberPerPage(numberPerPage);
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> addNote(Long userId, String note) {
		// TODO Auto-generated method stub
		try {
			userRepository.updateNoteByUserId(note, userId);
		} catch (Exception e) {
			throw new DBException("数据库错误",e);
		}
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> deleteUser(Long userId) {
		// TODO Auto-generated method stub
		User user = userRepository.findByUserId(userId);
		if (user == null || user.getType().equals(UserType.Admin.getCode())) {
			throw new AuthException("用户不存在");
		}
		try {
			userRepository.delete(userId);
		} catch (Exception e) {
			throw new DBException("数据库错误",e);
		}
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		
		return dataWrapper;
	}

}
