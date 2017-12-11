package com.lesson.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lesson.service.WXService;
import com.lesson.utils.DataWrapper;

@Controller
@RequestMapping(value = "/api/wx")
public class WXController {
	
	@Autowired
	WXService wXService;
	/**
	* @api {get} api/wx/getJSConfig 获取微信jsapi
	* @apiName wx_jsapi
	* @apiGroup wx
	* 
	* @apiParam {String} url * 当前地址
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "成功",
  	*		"data": {
  	*			"signature":"0173743ce6574f2b4257996d76589eaf855cb53f",
  	*			"errorCode":"ok",
  	*			"url":"http://www.baidu.com",
  	*			"nonceStr":"7cc4ec90-6a0b-4435-bc26-a54363832323",
  	*			"timestamp":"1506320613
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
	*  		"errorCode": "xx",
	*  		"data": "xxx",
	*  		"token": null
	*  		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/
	@RequestMapping(value = "getJSConfig", method = RequestMethod.GET)
	@ResponseBody
	public DataWrapper<Map<String, String>> getJSConfig(
			@RequestParam(name = "url", required = true) String url,
			HttpServletRequest request
			) {
		return wXService.getJSConfig(url);
	}

}
