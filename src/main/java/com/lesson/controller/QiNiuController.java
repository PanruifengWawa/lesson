package com.lesson.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lesson.enums.UserType;
import com.lesson.models.Token;
import com.lesson.repository.TokenRepository;
import com.lesson.service.QiNiuService;
import com.lesson.utils.CheckUser;
import com.lesson.utils.DataWrapper;

@Controller
@RequestMapping(value = "/api/qiniu")
public class QiNiuController {
	@Autowired
	QiNiuService qiNiuService;
	
	@Autowired
	TokenRepository tokenRepository;
	
	/**
	* @api {get} api/qiniu/getQiNiuToken 获取七牛uptoken
	* @apiName qiniu_getQiNiuToken
	* @apiGroup qiniu
	*
	* @apiHeader {String} token * 身份凭证（必须）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
	*  		"callStatus": "SUCCEED",
	*  		"errorCode": "No_Error",
	*  		"data": "2EkHs4sPHlelB-JYR5WuDp3jp9spsqyxIkluejva:7qWjUWr2RZTC71Xv3_tUwGWfh_E=:eyJzY29wZSI6ImN5Z2ZpbGUiLCJkZWFkbGluZSI6MTQ5MDg5NDE1NX0=",
	*  		"token": null,
	*  		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	*
	* @apiSuccessExample {json} Error-Response:
	* 	HTTP/1.1 200 ok
	* 	{
	*  		"callStatus": "FAILED",
	*  		"errorCode": "Error",
	*  		"data": null,
	*  		"token": null,
	*  		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/
	@RequestMapping(value="getQiNiuToken",method = RequestMethod.GET)
	@ResponseBody
	public DataWrapper<String> getQiNiuToken(
			HttpServletRequest request
			) {
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = {UserType.Admin, UserType.User};
		CheckUser.checkUser(token, userTypes);
		return qiNiuService.getQiNiuToken();
	}

}
