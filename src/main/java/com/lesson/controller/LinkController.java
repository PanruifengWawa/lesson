package com.lesson.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lesson.enums.UserType;
import com.lesson.models.Link;
import com.lesson.models.Token;
import com.lesson.repository.TokenRepository;
import com.lesson.service.LinkService;
import com.lesson.utils.CheckUser;
import com.lesson.utils.DataWrapper;

@Controller
@RequestMapping(value = "/api/link")
public class LinkController {
	
	@Autowired
	LinkService linkService;
	
	@Autowired
	TokenRepository tokenRepository;
	
	
	
	/**
	* @api {post} api/link/update 更新1000books链接（管理员）
	* @apiName link_update
	* @apiGroup link
	* 
	* @apiHeader {String} token 身份凭证
	* @apiParam {Long} linkId * 链接id（必须）
	* @apiParam {String} linkSrc * 链接地址（必须）
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
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public DataWrapper<Void> updateLink(
			@ModelAttribute Link link,
			HttpServletRequest request
			) {
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = { UserType.Admin};
		CheckUser.checkUser(token, userTypes);
		
		
		return linkService.updateLink(link);
	}
	
	
	/**
	* @api {get} api/link/get1000BooksLink 获取1000books的链接
	* @apiName link_get1000BooksLink
	* @apiGroup link
	* 
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "成功",
  	*		"data": {
    *			"linkId": 1,
    *			"linkSrc": "http://mp.weixin.qq.com/mp/homepage?__biz=MzIzNzYzMTc4Ng==&hid=5&sn=ceb19fbcab735c8a9fee0d7193effe82#wechat_redirect"
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
	@RequestMapping(value = "get1000BooksLink", method = RequestMethod.GET)
	@ResponseBody
	public DataWrapper<Link> get1000BooksLink(
			) {
		
		return linkService.getLinkById(new Long(1));
	}

}
