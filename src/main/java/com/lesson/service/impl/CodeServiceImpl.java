package com.lesson.service.impl;

import org.springframework.stereotype.Service;

import com.lesson.exceptions.CodeException;
import com.lesson.service.CodeService;
import com.lesson.utils.DataWrapper;
import com.lesson.utils.HttpUtil;
import com.lesson.utils.VerifyCodeManager;

@Service
public class CodeServiceImpl implements CodeService {

	@Override
	public DataWrapper<Void> getCode(String phone) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<>();
        String code = VerifyCodeManager.newPhoneCode(phone);
        
        if(code == null){
        	throw new CodeException("请勿重复发送");
        }
        HttpUtil httpUtil = new HttpUtil();
        boolean result = httpUtil.sendPhoneVerifyCode(code,phone);
       
        if (!result) {
        	throw new CodeException("验证码发送失败，请联系管理员");
		}
        return dataWrapper;
	}

}
