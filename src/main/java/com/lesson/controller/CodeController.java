package com.lesson.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lesson.service.CodeService;
import com.lesson.utils.DataWrapper;

@Controller
@RequestMapping(value = "/api/code")
public class CodeController {
	
	@Autowired
	CodeService codeService;
	
	
	
	/**
	* @api {get} api/code/getVerifyCode 获取验证码
	* @apiName code_getVerifyCode
	* @apiGroup code
	*
	* @apiParam {String} phone * 手机号码（必须）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "成功",
  	*		"data": null,
  	*		"token": null,
  	*		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	*
	* @apiSuccessExample {json} Error-Response:
	* 	HTTP/1.1 200 ok
	* 	{
	*  		"callStatus": "FAILED",
	*  		"errorCode": "验证码错误",
	*  		"data": "请勿重复发送",
	*  		"token": null
	*  		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/
	@RequestMapping(value = "getVerifyCode", method = RequestMethod.GET)
	@ResponseBody
	public DataWrapper<Void> getCode(
			@RequestParam(name = "phone", required = true) String phone
			) {
		
		return codeService.getCode(phone);
	}

}
