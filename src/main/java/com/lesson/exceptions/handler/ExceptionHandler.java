package com.lesson.exceptions.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lesson.enums.ErrorCodeEnum;
import com.lesson.exceptions.AuthException;
import com.lesson.exceptions.CodeException;
import com.lesson.exceptions.DBException;
import com.lesson.exceptions.ParameterException;
import com.lesson.utils.DataWrapper;




@ControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
	@ResponseBody
    public DataWrapper<String> exceptionProcess(RuntimeException ex) {
		ex.printStackTrace();
		
		DataWrapper<String> dataWrapper = new DataWrapper<String>();
		dataWrapper.setErrorCode(ErrorCodeEnum.未知错误);
		dataWrapper.setData(ex.getMessage());
        return dataWrapper;
    }
	
	@org.springframework.web.bind.annotation.ExceptionHandler(AuthException.class)
	@ResponseBody
    public DataWrapper<String> exceptionProcess(AuthException ex) {  
		return getReturns(ErrorCodeEnum.权限不足, ex.getMessage());
    }
	
	@org.springframework.web.bind.annotation.ExceptionHandler(DBException.class)
	@ResponseBody
    public DataWrapper<String> exceptionProcess(DBException ex) {  
		ex.printStackTrace();
		
		return getReturns(ErrorCodeEnum.数据库错误,ex.getMessage());
    }
	
	@org.springframework.web.bind.annotation.ExceptionHandler(ParameterException.class)
	@ResponseBody
    public DataWrapper<String> exceptionProcess(ParameterException ex) {  
		
		return getReturns(ErrorCodeEnum.参数错误,ex.getMessage());
    }
	
	
	@org.springframework.web.bind.annotation.ExceptionHandler(CodeException.class)
	@ResponseBody
    public DataWrapper<String> exceptionProcess(CodeException ex) {  
		
		return getReturns(ErrorCodeEnum.验证码错误,ex.getMessage());
    }
	
	public DataWrapper<String> getReturns(ErrorCodeEnum errorCodeEnum, String message) {
		DataWrapper<String> dataWrapper = new DataWrapper<String>();
		dataWrapper.setErrorCode(errorCodeEnum);
		dataWrapper.setData(message);
        return dataWrapper;
	}

}
