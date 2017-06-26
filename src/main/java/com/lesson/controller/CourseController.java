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
import com.lesson.models.Course;
import com.lesson.models.Token;
import com.lesson.repository.TokenRepository;
import com.lesson.service.CourseService;
import com.lesson.utils.CheckUser;
import com.lesson.utils.DataWrapper;

@Controller
@RequestMapping(value = "/api/course")
public class CourseController {
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	TokenRepository tokenRepository;

	/**
	* @api {post} api/course 创建课程（管理员）
	* @apiName course_add
	* @apiGroup course
	*
	* @apiParam {String} name * 课程名（必须）
	* @apiParam {String} imgSrc * 课程封面（必须）
	* @apiParam {String} isFree * 是否为公开课（必须,0-公开课,1-收费课）
	* @apiParam {String} type * 课程类型（必须,1-太阳课程,2-月亮课程,3-星星课程,4-自然拼读）
	* @apiParam {String} link * 课程购买链接（非必须）
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
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	DataWrapper<Void> addCourse(
			@ModelAttribute Course course,
			HttpServletRequest request
			) {
		
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = {UserType.Admin};
		CheckUser.checkUser(token, userTypes);
		
		
		return courseService.addCourse(course);
	}
	
	/**
	* @api {get} api/course/getCourseList 获取课程列表（管理员）
	* @apiName course_getCourseListFromAdmin
	* @apiGroup course
	*
	* @apiParam {String} isFree * 是否为公开课（必须,0-公开课,1-收费课）
	* @apiParam {String} type * 课程类型（必须,1-太阳课程,2-月亮课程,3-星星课程,4-自然拼读）
	* @apiParam {int} numberPerPage * 每页大小（非必须,1-10）
	* @apiParam {int} currentPage * 当前页（非必须）
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
	@RequestMapping(value = "getCourseList", method = RequestMethod.GET)
	@ResponseBody
	DataWrapper<List<Course>> getCourseList(
			@RequestParam(value = "isFree", required = false) Integer isFree,
			@RequestParam(value = "type", required = false) Integer type,
			@RequestParam(value = "numberPerPage", required = false) Integer numberPerPage,
    		@RequestParam(value = "currentPage", required = false) Integer currentPage,
			HttpServletRequest request
			) {
		
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = {UserType.Admin};
		CheckUser.checkUser(token, userTypes);
		
		return courseService.getCourseList(isFree, type, numberPerPage, currentPage);
	}
	
	/**
	* @api {post} api/course/{courseId} 更新课程（管理员）
	* @apiName course_update
	* @apiGroup course
	*
	* @apiParam {Long} courseId * 课程id（必须）
	* @apiParam {String} name * 课程名（非必须）
	* @apiParam {String} imgSrc * 课程封面（非必须）
	* @apiParam {String} isFree * 是否为公开课（非必须,0-公开课,1-收费课）
	* @apiParam {String} type * 课程类型（非必须,1-太阳课程,2-月亮课程,3-星星课程,4-自然拼读）
	* @apiParam {String} link * 课程购买链接（非必须）
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
	@RequestMapping(value = "{courseId}", method = RequestMethod.POST)
	@ResponseBody
	DataWrapper<Void> updateCourse(
			@PathVariable Long courseId,
			@ModelAttribute Course course,
			HttpServletRequest request
			) {
		
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = {UserType.Admin};
		CheckUser.checkUser(token, userTypes);
		
		course.setCourseId(courseId);
		return courseService.updateCourse(course);
	}
	
	/**
	* @api {delete} api/course/{courseId} 删除课程（管理员）
	* @apiName course_delete
	* @apiGroup course
	*
	* @apiParam {Long} courseId * 课程id（必须）
	* 
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
	@RequestMapping(value = "{courseId}", method = RequestMethod.DELETE)
	@ResponseBody
	DataWrapper<Void> deleteCourse(
			@PathVariable Long courseId,
			HttpServletRequest request
			) {
		
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = {UserType.Admin};
		CheckUser.checkUser(token, userTypes);
		
		return courseService.deleteCourse(courseId);
	}
	
	
	
	/**
	* @api {get} api/course/free 获取体验课列表（游客,不需要身份验证）
	* @apiName course_free
	* @apiGroup course
	*
	* @apiParam {String} type * 课程类型（非必须,1-太阳课程,2-月亮课程,3-星星课程,4-自然拼读）
	* @apiParam {int} numberPerPage * 每页大小（非必须,1-10）
	* @apiParam {int} currentPage * 当前页（非必须）
	* 
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
	@RequestMapping(value = "free", method = RequestMethod.GET)
	@ResponseBody
	DataWrapper<List<Course>> getFreeCourseList(
			@RequestParam(value = "type", required = false) Integer type,
			@RequestParam(value = "numberPerPage", required = false) Integer numberPerPage,
    		@RequestParam(value = "currentPage", required = false) Integer currentPage
			) {
		return courseService.getFreeCourseList(type, numberPerPage, currentPage);
	}
	
	/**
	* @api {get} api/course 获取课程列表（用户，未完成，需要跟用户购买课程关联）
	* @apiName course_free
	* @apiGroup course
	*
	* @apiParam {String} type * 课程类型（必须,1-太阳课程,2-月亮课程,3-星星课程,4-自然拼读）
	* @apiParam {int} numberPerPage * 每页大小（非必须,1-10）
	* @apiParam {int} currentPage * 当前页（非必须）
	* 
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "成功",
  	*		"data": "未完成",
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
	DataWrapper<List<Course>> getCourseList(
			@RequestParam(value = "type", required = true) Integer type,
			@RequestParam(value = "numberPerPage", required = false) Integer numberPerPage,
    		@RequestParam(value = "currentPage", required = false) Integer currentPage
			) {
		return courseService.getCourseList(type, numberPerPage, currentPage);
	}
	
	

}
