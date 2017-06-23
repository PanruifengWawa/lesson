package com.lesson.utils;


import com.lesson.enums.UserType;
import com.lesson.exceptions.AuthException;
import com.lesson.models.Token;

public class CheckUser {
	public static void checkUser(Token token, UserType[] userTypes) {
		if (token != null) {//可以通过检查用户类型来进行对应的操作
			boolean auth = false;
			for(UserType userType : userTypes) {
				if (token.getUserType().equals(userType.getCode())) {
					auth = true;
					break;
				}
			 }
			 
			 if (!auth) {
				 throw new AuthException("用户权限不足");
			 }
			 
			 
		 } else {
			 
			 throw new AuthException("用户未登录");
		 }
	}

}
