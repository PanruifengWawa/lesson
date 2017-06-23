package com.lesson.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lesson.enums.UserType;
import com.lesson.models.Token;
import com.lesson.models.User;
import com.lesson.repository.TokenRepository;
import com.lesson.service.UserService;
import com.lesson.utils.CheckUser;
import com.lesson.utils.DataWrapper;
import com.lesson.utils.DateUtil;

@Controller
@RequestMapping(value = "/api/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	TokenRepository tokenRepository;
	
	
	/**
	* @api {post} api/user 注册（用户）
	* @apiName user_register
	* @apiGroup user
	*
	* @apiParam {String} phone * 手机号码（必须）
	* @apiParam {String} password * 密码（必须）
	* @apiParam {String} parent * 我是（必须）
	* @apiParam {String} nickName * 宝宝昵称（必须）
	* @apiParam {String} gender * 性别（必须,F-女,M-男）
	* @apiParam {String} birthdayStr * 生日（必须,"yyyy-mm-dd"）
	* @apiParam {String} city * 城市（必须）
	* @apiParam {String} code * 验证码（必须）
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
	*  		"data": "验证码不存在",
	*  		"token": null
	*  		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public DataWrapper<Void> register(
			@ModelAttribute User user,
			@RequestParam(name = "birthdayStr", required = true) String birthdayStr,
			@RequestParam(name = "code", required = true) String code
			) {
		user.setBirthday(DateUtil.parse(birthdayStr));
		return userService.register(user, code);
	}
	
	
	/**
	* @api {post} api/user/login 登录（管理员、用户）
	* @apiName user_login
	* @apiGroup user
	*
	* @apiParam {String} phone * 手机号码（必须）
	* @apiParam {String} password * 密码（必须）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "成功",
  	*		"data": {
    *			"userId": 1,
    *			"phone": "13761463756",
    *			"password": null,
    *			"nickName": "大潘",
    *			"gender": "F",
    *			"birthday": 751219200000,
    *			"city": "上海",
    *			"type": 1,
    *			"headSrc": null,
    *			"note": null,
    *			"parent": "妈妈",
    *			"registerDate": 1222222
    *		},
  	*		"token": "SKd68ab88b-5d06-4fd0-9ff4-e13d3d7e8d7c",
  	*		"numberPerPage": 0,
  	*		"currentPage": 0,
  	*		"totalNumber": 0,
  	*		"totalPage": 0
	*	}
	*
	* @apiSuccessExample {json} Error-Response:
	* 	HTTP/1.1 200 ok
	* 	{
	*  		"callStatus": "FAILED",
	*  		"errorCode": "权限错误",
	*  		"data": "用户未注册",
	*  		"token": null
	*  		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
	public DataWrapper<User> login(
			@RequestParam(name = "phone", required = true) String phone,
			@RequestParam(name = "password", required = true) String password
			) {
		return userService.login(phone, password);
	}
	
	
	/**
	* @api {post} api/user/forgetPWD 忘记密码（用户）
	* @apiName user_forgetPWD
	* @apiGroup user
	*
	* @apiParam {String} phone * 手机号码（必须）
	* @apiParam {String} newPassword * 新密码密码（必须）
	* @apiParam {String} code * 验证码（必须）
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
	*  		"data": "验证码不存在",
	*  		"token": null
	*  		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/
	@RequestMapping(value = "forgetPWD", method = RequestMethod.POST)
	@ResponseBody
	public DataWrapper<Void> forgetPwd(
			@RequestParam(name = "phone", required = true) String phone,
			@RequestParam(name = "code", required = true) String code,
			@RequestParam(name = "newPassword", required = true) String newPassword
			) {
		return userService.forgetPwd(phone, code, newPassword);
	}
	
	
	/**
	* @api {post} api/user/changePWD 修改密码（用户，管理员）
	* @apiName user_changePWD
	* @apiGroup user
	* 
	* @apiHeader {String} token 身份凭证
	*
	* @apiParam {String} newPassword * 新密码密码（必须）
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
	*  		"errorCode": "权限错误",
	*  		"data": "用户未登录",
	*  		"token": null
	*  		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/
	@RequestMapping(value = "changePWD", method = RequestMethod.POST)
	@ResponseBody
	public DataWrapper<Void> changePwd(
			@RequestParam(name = "newPassword", required = true) String newPassword,
			HttpServletRequest request
			) {
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = { UserType.User, UserType.Admin };
		CheckUser.checkUser(token, userTypes);
		return userService.changePwd(token.getUserId(), newPassword);
	}
	
	
	/**
	* @api {post} api/user/update 修改信息（用户）
	* @apiName user_update
	* @apiGroup user
	* 
	* @apiHeader {String} token 身份凭证
	*
	* @apiParam {String} nickName * 宝宝昵称（非必须）
	* @apiParam {String} parent * 我是（非必须）
	* @apiParam {String} gender * 性别（非必须,F-女,M-男）
	* @apiParam {String} birthdayStr * 生日（非必须,"yyyy-mm-dd"）
	* @apiParam {String} city * 城市（非必须）
	* @apiParam {String} headSrc * 头像路径（非必须）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "成功",
  	*		"data": {
    *			"userId": 1,
    *			"phone": "13761463756",
    *			"password": null,
    *			"nickName": "大潘",
    *			"gender": "F",
    *			"birthday": 751219200000,
    *			"city": "上海",
    *			"type": 1,
    *			"headSrc": null,
    *			"note": null,
    *			"parent": "妈妈",
    *			"registerDate": 1222222
    *		},
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
	*  		"errorCode": "权限错误",
	*  		"data": "用户未登录",
	*  		"token": null
	*  		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public DataWrapper<User> changeUserInfo(
			@ModelAttribute User user,
			@RequestParam(name = "birthdayStr", required = false) String birthdayStr,
			HttpServletRequest request
			) {
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = {UserType.User};
		CheckUser.checkUser(token, userTypes);
		

		user.setBirthday(DateUtil.parse(birthdayStr));
		return userService.changeUserInfo(token.getUserId(), user);
	}
	
	
	/**
	* @api {get} api/user 获取用户详情（用户）
	* @apiName user_get
	* @apiGroup user
	* 
	* @apiHeader {String} token 身份凭证
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "成功",
  	*		"data": {
    *			"userId": 1,
    *			"phone": "13761463756",
    *			"password": null,
    *			"nickName": "大潘",
    *			"gender": "F",
    *			"birthday": 751219200000,
    *			"city": "上海",
    *			"type": 1,
    *			"headSrc": null,
    *			"note": null,
    *			"parent": "妈妈",
    *			"registerDate": 1222222
    *		},
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
	*  		"errorCode": "权限错误",
	*  		"data": "用户未登录",
	*  		"token": null
	*  		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public DataWrapper<User> getUserDetails(
			HttpServletRequest request
			) {
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = { UserType.User};
		CheckUser.checkUser(token, userTypes);
		return userService.getUserDetails(token.getUserId());
	}
	
	
	/**
	* @api {get} api/user/getUserList 获取用户列表（管理员）
	* @apiName user_getUserList
	* @apiGroup user
	* 
	* @apiHeader {String} token 身份凭证
	* @apiParam {int} numberPerPage * 每页大小（非必须）
	* @apiParam {int} currentPage * 当前页（非必须）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "成功",
  	*		"data": [{
    *			"userId": 1,
    *			"phone": "13761463756",
    *			"password": null,
    *			"nickName": "大潘",
    *			"gender": "F",
    *			"birthday": 751219200000,
    *			"city": "上海",
    *			"type": 1,
    *			"headSrc": null,
    *			"note": null,
    *			"parent": "妈妈",
    *			"registerDate": 1222222
    *		}],
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
	*  		"errorCode": "权限错误",
	*  		"data": "用户未登录",
	*  		"token": null
	*  		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/
	@RequestMapping(value = "getUserList", method = RequestMethod.GET)
	@ResponseBody
	public DataWrapper<List<User>> getUserList(
			@RequestParam(value = "numberPerPage", required = false) Integer numberPerPage,
    		@RequestParam(value = "currentPage", required = false) Integer currentPage,
			HttpServletRequest request
			) {
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = { UserType.Admin};
		CheckUser.checkUser(token, userTypes);
		return userService.getUserList(numberPerPage, currentPage);
	}
	
	
	/**
	* @api {post} api/user/{userId} 为用户添加备注（管理员）
	* @apiName user_addNote
	* @apiGroup user
	* 
	* @apiHeader {String} token 身份凭证
	* @apiParam {Long} userId * 用户id（必须）
	* @apiParam {String} note * 备注（必须）
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
	*  		"errorCode": "权限错误",
	*  		"data": "用户未登录",
	*  		"token": null
	*  		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/
	@RequestMapping(value = "{userId}", method = RequestMethod.POST)
	@ResponseBody
	public DataWrapper<Void> addNote(
			@PathVariable Long userId,
    		@RequestParam(value = "note", required = true) String note,
			HttpServletRequest request
			) {
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = { UserType.Admin};
		CheckUser.checkUser(token, userTypes);
		return userService.addNote(userId, note);
	}

	
	/**
	* @api {delete} api/user/{userId} 删除用户（管理员）
	* @apiName user_delete
	* @apiGroup user
	* 
	* @apiHeader {String} token 身份凭证
	* @apiParam {Long} userId * 用户id（必须）
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
	*  		"errorCode": "权限错误",
	*  		"data": "用户未登录",
	*  		"token": null
	*  		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/

	@RequestMapping(value = "{userId}", method = RequestMethod.DELETE)
	@ResponseBody
	public DataWrapper<Void> deleteUser(
			@PathVariable Long userId,
			HttpServletRequest request
			) {
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = { UserType.Admin};
		CheckUser.checkUser(token, userTypes);
		return userService.deleteUser(userId);
	}
	
	
	

}
