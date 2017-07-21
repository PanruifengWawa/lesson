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
import com.lesson.models.CourseArrangement;
import com.lesson.models.CourseCode;
import com.lesson.models.CourseContent;
import com.lesson.models.Token;
import com.lesson.repository.TokenRepository;
import com.lesson.service.CourseCodeService;
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
	
	@Autowired
	CourseCodeService courseCodeService;
	//course

	/**
	* @api {post} api/course 创建课程（管理员）
	* @apiName course_add
	* @apiGroup course
	* @apiHeader {String} token 身份凭证
	*
	* @apiParam {String} name * 课程名（必须）
	* @apiParam {String} imgSrc * 课程封面（必须）
	* @apiParam {int} isFree * 是否为公开课（必须,0-收费课,1-公开课）
	* @apiParam {int} type * 课程类型（必须,1-太阳课程,2-月亮课程,3-星星课程,4-自然拼读）
	* @apiParam {String} link * 课程购买链接（非必须）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "成功",
  	*		"data": {
    *			"courseId": 8,
    *			"name": "课程7",
    *			"imgSrc": "imgSrc",
    *			"isFree": 1,
    *			"type": 4,
    *			"createDate": 1498491339000,
    *			"link": "link",
    *			"state": 0 //0-未开课,1-已开课
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
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public DataWrapper<Course> addCourse(
			@ModelAttribute Course course,
			HttpServletRequest request
			) {
		
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = {UserType.Admin};
		CheckUser.checkUser(token, userTypes);
		
		
		return courseService.addCourse(course);
	}
	
	/**
	* @api {post} api/course/{courseId}/changeState 修改课程状态（管理员）
	* @apiName course_changeState
	* @apiGroup course
	* @apiHeader {String} token 身份凭证
	*
	* @apiParam {Long} courseId * 课程id（必须）
	* @apiParam {int} state * 课程状态（必须,0-未开课,1-已开课）
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
	@RequestMapping(value = "{courseId}/changeState", method = RequestMethod.POST)
	@ResponseBody
	public DataWrapper<Void> changeState(
			@PathVariable Long courseId,
			@RequestParam(value = "state", required = true) Integer state,
			HttpServletRequest request
			) {
		
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = {UserType.Admin};
		CheckUser.checkUser(token, userTypes);
		
		
		return courseService.changeState(courseId, state);
	}
	
	/**
	* @api {get} api/course/getCourseList 获取课程列表（管理员）
	* @apiName course_getCourseListFromAdmin
	* @apiGroup course
	* @apiHeader {String} token 身份凭证
	*
	* @apiParam {int} isFree * 是否为公开课（非必须,0-收费课,1-公开课）
	* @apiParam {int} type * 课程类型（非必须,1-太阳课程,2-月亮课程,3-星星课程,4-自然拼读）
	* @apiParam {int} numberPerPage * 每页大小（非必须,默认不分页）
	* @apiParam {int} currentPage * 当前页（非必须）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "成功",
  	*		"data": [{
    *			"courseId": 8,
    *			"name": "课程7",
    *			"imgSrc": "imgSrc",
    *			"isFree": 1,
    *			"type": 4,
    *			"createDate": 1498491339000,
    *			"link": "link",
    *			"state": 1
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
	@RequestMapping(value = "getCourseList", method = RequestMethod.GET)
	@ResponseBody
	public DataWrapper<List<Course>> getCourseList(
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
	* @apiHeader {String} token 身份凭证
	*
	* @apiParam {Long} courseId * 课程id（必须）
	* @apiParam {String} name * 课程名（非必须）
	* @apiParam {String} imgSrc * 课程封面（非必须）
	* @apiParam {int} isFree * 是否为公开课（非必须,0-收费课,1-公开课）
	* @apiParam {int} type * 课程类型（非必须,1-太阳课程,2-月亮课程,3-星星课程,4-自然拼读）
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
	public DataWrapper<Void> updateCourse(
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
	* @apiHeader {String} token 身份凭证
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
	public DataWrapper<Void> deleteCourse(
			@PathVariable Long courseId,
			HttpServletRequest request
			) {
		
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = {UserType.Admin};
		CheckUser.checkUser(token, userTypes);
		
		return courseService.deleteCourse(courseId);
	}
	
	/**
	* @api {get} api/course/{courseId} 获取课程详情（管理员）
	* @apiName course_getDetails
	* @apiGroup course
	* @apiHeader {String} token 身份凭证
	*
	* @apiParam {Long} courseId * 课程id（必须）
	* 
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "成功",
  	*		"data": {
    *			"courseId": 8,
    *			"name": "课程7",
    *			"imgSrc": "imgSrc",
    *			"isFree": 1,
    *			"type": 4,
    *			"createDate": 1498491339000,
    *			"link": "link",
    *			"state": 1
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
	@RequestMapping(value = "{courseId}", method = RequestMethod.GET)
	@ResponseBody
	public DataWrapper<Course> getCourseDetails(
			@PathVariable Long courseId,
			HttpServletRequest request
			) {
		
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = {UserType.Admin};
		CheckUser.checkUser(token, userTypes);
		
		return courseService.getCourseDetails(courseId);
	}
	
	
	
	/**
	* @api {get} api/course/free 获取体验课列表（游客,不需要身份验证）
	* @apiName course_free
	* @apiGroup course
	*
	* @apiParam {int} type * 课程类型（非必须,1-太阳课程,2-月亮课程,3-星星课程,4-自然拼读）
	* @apiParam {int} numberPerPage * 每页大小（非必须,默认不分页）
	* @apiParam {int} currentPage * 当前页（非必须）
	* 
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "成功",
  	*		"data": [{
    *        			"courseId": 8,
    *        			"name": "课程7",
    *        			"imgSrc": "imgSrc",
    *        			"isFree": 1,
    *        			"type": 4,
    *        			"createDate": 1498491339000,
    *        			"link": "link",
    *        			"state": 1
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
	@RequestMapping(value = "free", method = RequestMethod.GET)
	@ResponseBody
	public DataWrapper<List<Course>> getFreeCourseList(
			@RequestParam(value = "type", required = false) Integer type,
			@RequestParam(value = "numberPerPage", required = false) Integer numberPerPage,
    		@RequestParam(value = "currentPage", required = false) Integer currentPage
			) {
		return courseService.getFreeCourseList(type, numberPerPage, currentPage);
	}
	
	/**
	* @api {get} api/course 获取课程列表（用户）
	* @apiName course_user
	* @apiGroup course
	* 
	* @apiHeader {String} token 身份凭证
	*
	* @apiParam {int} type * 课程类型（必须,1-太阳课程,2-月亮课程,3-星星课程,4-自然拼读）
	* @apiParam {int} numberPerPage * 每页大小（非必须,默认分页）
	* @apiParam {int} currentPage * 当前页（非必须）
	* 
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "成功",
  	*		"data": [{
    *        			"courseId": 8,
    *        			"name": "课程7",
    *        			"imgSrc": "imgSrc",
    *        			"isFree": 1,
    *        			"type": 4,
    *        			"createDate": 1498491339000,
    *        			"link": "link",
    *        			"isBought" : 1,//是否已购买
    *        			"state": 1,
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
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public DataWrapper<List<Course>> getCourseList(
			@RequestParam(value = "type", required = true) Integer type,
			@RequestParam(value = "numberPerPage", required = false) Integer numberPerPage,
    		@RequestParam(value = "currentPage", required = false) Integer currentPage,
    		HttpServletRequest request
			) {
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = {UserType.User};
		CheckUser.checkUser(token, userTypes);
		
		return courseService.getCourseListFromUser(token.getUserId(), type, numberPerPage, currentPage);
	}
	
	
	//course arrangement
	/**
	* @api {post} api/course/{courseId}/arrangement 添加课程安排（管理员）
	* @apiName course_arrangement_add
	* @apiGroup course_arrangement
	* @apiHeader {String} token 身份凭证
	*
	* @apiParam {Long} courseId * 课程id（必须）
	* @apiParam {String} name * 课程安排标题（必须）
	* @apiParam {String} imgSrc * 课程安排封面（必须）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "成功",
  	*		"data": {
    *			"courseArrangementId": 3,
    *			"imgSrc": "imgSrc",
    *			"name": "Lesson7-8:测试",
    *			"courseId": 3,
    *			"courseContent": null,
    *			"state": 0,
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
	@RequestMapping(value = "{courseId}/arrangement", method = RequestMethod.POST)
	@ResponseBody
	public DataWrapper<CourseArrangement> addArrangeMent(
			@PathVariable Long courseId,
			@ModelAttribute CourseArrangement courseArrangement,
			HttpServletRequest request
			) {
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = {UserType.Admin};
		CheckUser.checkUser(token, userTypes);
		
		courseArrangement.setCourseId(courseId);
		return courseService.addArrangeMent(courseArrangement);
	}
	
	/**
	* @api {get} api/course/{courseId}/arrangement 获取课程安排（管理员,已经购买课程的用户,体验课）
	* @apiName course_arrangement_get
	* @apiGroup course_arrangement
	* 
	* @apiHeader {String} token 身份凭证(非必须)
	*
	* @apiParam {Long} courseId * 课程id（必须）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "成功",
  	*		"data": [{
    *			"courseArrangementId": 7,
    *			"imgSrc": "imgSrc12",
    *			"name": "lesson1-10:大课程",
    *			"courseId": 8,
    *			"state": 1,
    *			"courseContent": [
    *				{
    *					"courseContentId": 13,
    *					"name": "lesson1",
    *					"subName":"",
    *					"state": 1
    *				},
    *				{
    *					"courseContentId": 14,
    *					"name": "lesson2",
    *					"subName": "",
    *					"state": 1
    *				}
    *			]
    *		},
    *		{
    *			"courseArrangementId": 8,
    *			"imgSrc": "abc",
    *			"name": "lesson11-20",
    *			"courseId": 8,
    *			"state": 1,
    *			"courseContent": []
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
	@RequestMapping(value = "{courseId}/arrangement", method = RequestMethod.GET)
	@ResponseBody
	public DataWrapper<List<CourseArrangement>> getArrangeMentList(
			@PathVariable Long courseId,
			HttpServletRequest request
			) {
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		return courseService.getArrangeMentList(courseId, token);
	}
	
	/**
	* @api {post} api/course/{courseId}/arrangement/{courseArrangementId} 修改课程安排（管理员）
	* @apiName course_arrangement_update
	* @apiGroup course_arrangement
	* 
	* @apiHeader {String} token 身份凭证
	*
	* @apiParam {Long} courseId * 课程id（必须）
	* @apiParam {Long} courseArrangementId * 课程安排id（必须）
	* @apiParam {String} name * 课程安排名称（非必须）
	* @apiParam {String} imgSrc * 课程安排封面（非必须）
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
	@RequestMapping(value = "{courseId}/arrangement/{courseArrangementId}", method = RequestMethod.POST)
	@ResponseBody
	public DataWrapper<Void> updateCourseArrangement(
			@PathVariable Long courseId,
			@PathVariable Long courseArrangementId,
			@ModelAttribute CourseArrangement courseArrangement,
			HttpServletRequest request
			) {
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = {UserType.Admin};
		CheckUser.checkUser(token, userTypes);
		
		return courseService.updateCourseArrangement(courseArrangementId, courseArrangement);
	}
	
	
	/**
	* @api {post} api/course/{courseId}/arrangement/{courseArrangementId}/changeState 修改课程安排状态（管理员）
	* @apiName course_changeState_arrangement
	* @apiGroup course_arrangement
	* @apiHeader {String} token 身份凭证
	*
	* @apiParam {Long} courseId * 课程id（必须）
	* @apiParam {Long} courseArrangementId * 课程安排id（必须）
	* @apiParam {int} state * 课程安排状态（必须,0-未开课,1-已开课）
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
	@RequestMapping(value = "{courseId}/arrangement/{courseArrangementId}/changeState", method = RequestMethod.POST)
	@ResponseBody
	public DataWrapper<Void> changeArrangementState(
			@PathVariable Long courseId,
			@PathVariable Long courseArrangementId,
			@RequestParam(value = "state", required = true) Integer state,
			HttpServletRequest request
			) {
		
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = {UserType.Admin};
		CheckUser.checkUser(token, userTypes);
		
		
		return courseService.changeArrangementState(courseArrangementId, state);
	}
	
	/**
	* @api {delete} api/course/{courseId}/arrangement/{courseArrangementId} 删除课程安排（管理员）
	* @apiName course_arrangement_delete
	* @apiGroup course_arrangement
	* 
	* @apiHeader {String} token 身份凭证
	*
	* @apiParam {Long} courseId * 课程id（必须）
	* @apiParam {Long} courseArrangementId * 课程安排id（必须）
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
	@RequestMapping(value = "{courseId}/arrangement/{courseArrangementId}", method = RequestMethod.DELETE)
	@ResponseBody
	public DataWrapper<Void> deleteCourseArrangement(
			@PathVariable Long courseArrangementId,
			HttpServletRequest request
			) {
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = {UserType.Admin};
		CheckUser.checkUser(token, userTypes);
		
		return courseService.deleteCourseArrangement(courseArrangementId);
	}
	
	
	/**
	* @api {get} api/course/{courseId}/arrangement/{courseArrangementId} 获取课程安排详情（管理员）
	* @apiName course_arrangement_getDetails
	* @apiGroup course_arrangement
	* 
	* @apiHeader {String} token 身份凭证
	*
	* @apiParam {Long} courseId * 课程id（必须）
	* @apiParam {Long} courseArrangementId * 课程安排id（必须）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "成功",
  	*		"data": {
    *			"courseArrangementId": 7,
    *			"imgSrc": "imgSrc12",
    *			"name": "lesson1-10:大课程",
    *			"courseId": 3,
    *			"state": 1,
    *			"courseContent": [//这个字段你不要管，是我用的框架级联出来的
    *				{
    *					"courseContentId": 13,
    *					"name": "lesson1",
    *					"subName":"",
    *					"state": 1,
    *				}
    *			]
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
	@RequestMapping(value = "{courseId}/arrangement/{courseArrangementId}", method = RequestMethod.GET)
	@ResponseBody
	public DataWrapper<CourseArrangement> getCourseArrangementDetails(
			@PathVariable Long courseArrangementId,
			HttpServletRequest request
			) {
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = {UserType.Admin};
		CheckUser.checkUser(token, userTypes);
		
		return courseService.getArrangeMentDetails(courseArrangementId);
	}
	
	
	
	//course content
	/**
	* @api {post} api/course/{courseId}/arrangement/{courseArrangementId}/content 添加课程内容（管理员）
	* @apiName course_content_add
	* @apiGroup course_content
	* 
	* @apiHeader {String} token 身份凭证
	*
	* @apiParam {Long} courseId * 课程id（必须）
	* @apiParam {Long} courseArrangementId * 课程安排id（必须）
	* @apiParam {String} name * 课程内容标题（必须）
	* @apiParam {String} subName * 课程内容副标题（非必须）
	* @apiParam {String} content * 课程内容（必须）
	* @apiParam {String} bookName * 课程内容对应的书籍（必须）
	* @apiParam {String} bookImgSrc * 课程内容对应的书籍封面（必须）
	* 
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "成功",
  	*		"data": {
    *			"courseContentId": 6,
    *			"name": "lesson3",
    *			"subName": "哈哈哈",
    *			"content": "content",
    *			"courseId": 3,
    *			"courseArrangementId": 1,
    *			"bookName": "测试书籍2",
    *			"bookImgSrc": "http://www.baidu.com",
    *			"state": 1,
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
	@RequestMapping(value = "{courseId}/arrangement/{courseArrangementId}/content", method = RequestMethod.POST)
	@ResponseBody
	public DataWrapper<CourseContent> addCourseContent(
			@PathVariable Long courseId,
			@PathVariable Long courseArrangementId,
			@ModelAttribute CourseContent courseContent,
			HttpServletRequest request
			) {
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = {UserType.Admin};
		CheckUser.checkUser(token, userTypes);
		
		courseContent.setCourseId(courseId);
		courseContent.setCourseArrangementId(courseArrangementId);
		return courseService.addCourseContent(courseContent);
	}
	
	
	/**
	* @api {delete} api/course/{courseId}/arrangement/{courseArrangementId}/content/{courseContentId} 删除课程内容（管理员）
	* @apiName course_content_delete
	* @apiGroup course_content
	* 
	* @apiHeader {String} token 身份凭证
	*
	* @apiParam {Long} courseId * 课程id（必须）
	* @apiParam {Long} courseArrangementId * 课程安排id（必须）
	* @apiParam {Long} courseContentId * 课程内容id（必须）
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
	@RequestMapping(value = "{courseId}/arrangement/{courseArrangementId}/content/{courseContentId}", method = RequestMethod.DELETE)
	@ResponseBody
	public DataWrapper<Void> deleteCourseContent(
			@PathVariable Long courseId,
			@PathVariable Long courseArrangementId,
			@PathVariable Long courseContentId,
			HttpServletRequest request
			) {
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = {UserType.Admin};
		CheckUser.checkUser(token, userTypes);
		
		return courseService.deleteCourseContent(courseContentId);
	}
	
	/**
	* @api {post} api/course/{courseId}/arrangement/{courseArrangementId}/content/{courseContentId}/changeState 修改课程内容状态（管理员）
	* @apiName course_changeState_content
	* @apiGroup course_content
	* @apiHeader {String} token 身份凭证
	*
	* @apiParam {Long} courseId * 课程id（必须）
	* @apiParam {Long} courseArrangementId * 课程安排id（必须）
	* @apiParam {Long} courseContentId * 课程安排id（必须）
	* @apiParam {int} state * 课程安排状态（必须,0-未开课,1-已开课）
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
	@RequestMapping(value = "{courseId}/arrangement/{courseArrangementId}/content/{courseContentId}/changeState", method = RequestMethod.POST)
	@ResponseBody
	public DataWrapper<Void> changeContentState(
			@PathVariable Long courseId,
			@PathVariable Long courseArrangementId,
			@PathVariable Long courseContentId,
			@RequestParam(value = "state", required = true) Integer state,
			HttpServletRequest request
			) {
		
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = {UserType.Admin};
		CheckUser.checkUser(token, userTypes);
		
		
		return courseService.changeContentState(courseContentId, state);
	}
	
	
	/**
	* @api {post} api/course/{courseId}/arrangement/{courseArrangementId}/content/{courseContentId} 修改课程内容（管理员）
	* @apiName course_content_update
	* @apiGroup course_content
	* 
	* @apiHeader {String} token 身份凭证
	*
	* @apiParam {Long} courseId * 课程id（必须）
	* @apiParam {Long} courseArrangementId * 课程安排id（必须）
	* @apiParam {Long} courseContentId * 课程内容id（必须）
	* @apiParam {String} name * 课程内容标题（非必须）
	* @apiParam {String} subName * 课程内容副标题（非必须）
	* @apiParam {String} content * 课程内容（非必须）
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
	@RequestMapping(value = "{courseId}/arrangement/{courseArrangementId}/content/{courseContentId}", method = RequestMethod.POST)
	@ResponseBody
	public DataWrapper<Void> updateCourseContent(
			@PathVariable Long courseId,
			@PathVariable Long courseArrangementId,
			@PathVariable Long courseContentId,
			@ModelAttribute CourseContent courseContent,
			HttpServletRequest request
			) {
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = {UserType.Admin};
		CheckUser.checkUser(token, userTypes);
		
		
		courseContent.setCourseId(courseId);
		courseContent.setCourseArrangementId(courseArrangementId);
		courseContent.setCourseContentId(courseContentId);
		return courseService.updateCourseContent(courseContent);
	}
	
	/**
	* @api {get} api/course/{courseId}/arrangement/{courseArrangementId}/content/{courseContentId} 获取课程内容（管理员,已经购买课程的用户,体验课）
	* @apiName course_content_get
	* @apiGroup course_content
	* 
	* @apiHeader {String} token 身份凭证 (非必须)
	*
	* @apiParam {Long} courseId * 课程id（必须）
	* @apiParam {Long} courseArrangementId * 课程安排id（必须）
	* @apiParam {Long} courseContentId * 课程内容id（必须）
	* 
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "成功",
  	*		"data": {
    *			"courseContentId": 6,
    *			"name": "lesson3",
    *			"subName": "哈哈哈",
    *			"content": "content",
    *			"courseId": 3,
    *			"courseArrangementId": 1,
    *			"bookName": "测试书籍2",
    *			"bookImgSrc": "http://www.baidu.com",
    *			"state": 1
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
	@RequestMapping(value = "{courseId}/arrangement/{courseArrangementId}/content/{courseContentId}", method = RequestMethod.GET)
	@ResponseBody
	public DataWrapper<CourseContent> getCourseContentDetails(
			@PathVariable Long courseId,
			@PathVariable Long courseArrangementId,
			@PathVariable Long courseContentId,
			HttpServletRequest request
			) {
		
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		return courseService.getCourseContentDetails(courseId, courseContentId, token);
	}
	
	
	//course code
	
	/**
	* @api {post} api/course/{courseId}/courseCode 生成课程激活码（管理员）
	* @apiName course_courseCode_add
	* @apiGroup course_courseCode
	* 
	* @apiHeader {String} token 身份凭证
	*
	* @apiParam {Long} courseId * 课程id（必须）
	* @apiParam {int} num * 生成的数量（必须,1-100）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "成功",
  	*		"data": [ 
  	*			"cdfe8efdbc6d4dc99a0e710fad5d77e6",
    *			"7318fce3a00a4414b35344656a42ebed"
    *		],
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
	@RequestMapping(value = "{courseId}/courseCode", method = RequestMethod.POST)
	@ResponseBody
	public DataWrapper<List<String>> addCourseCode(
			@PathVariable Long courseId,
			@RequestParam(value = "num", required = true) Integer num,
			HttpServletRequest request
			) {
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = {UserType.Admin};
		CheckUser.checkUser(token, userTypes);
		
		return courseCodeService.addCourseCode(courseId, token.getUserId(), num);
	}
	
	
	/**
	* @api {get} api/course/{courseId}/courseCode 获取课程激活码列表（管理员）
	* @apiName course_courseCode_get
	* @apiGroup course_courseCode
	* 
	* @apiHeader {String} token 身份凭证
	*
	* @apiParam {Long} courseId * 课程id（必须）
	* @apiParam {int} numberPerPage * 每页大小（非必须,1-10）
	* @apiParam {int} currentPage * 当前页（非必须）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "成功",
  	*		"data": [{
    *			"courseCodeId": 151,
    *			"courseCodeStr": "2c8fbd1a21774cb0b766bb4ac6568a52",
    *			"courseId": 4,
    *			"userId": 1,//未使用的情况下，这里的user就是admin
    *			"isUsed": 0,//0-已使用，1-未使用
    *			"useDate": null//使用日期
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
	@RequestMapping(value = "{courseId}/courseCode", method = RequestMethod.GET)
	@ResponseBody
	public DataWrapper<List<CourseCode>> getCourseCodeList(
			@PathVariable Long courseId,
			@RequestParam(value = "numberPerPage", required = false) Integer numberPerPage,
    		@RequestParam(value = "currentPage", required = false) Integer currentPage,
			HttpServletRequest request
			) {
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = {UserType.Admin};
		CheckUser.checkUser(token, userTypes);
		
		return courseCodeService.getCourseCodeList(courseId, numberPerPage, currentPage);
	}
	
	

	/**
	* @api {delete} api/course/{courseId}/courseCode/{courseCodeId} 删除激活码（管理员）
	* @apiName course_courseCode_delete
	* @apiGroup course_courseCode
	* 
	* @apiHeader {String} token 身份凭证
	*
	* @apiParam {Long} courseId * 课程id（必须）
	* @apiParam {Long} courseCodeId * 课程激活码id（必须）
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
	@RequestMapping(value = "{courseId}/courseCode/{courseCodeId}", method = RequestMethod.DELETE)
	@ResponseBody
	public DataWrapper<Void> deleteCourseCode(
			@PathVariable Long courseId,
			@PathVariable Long courseCodeId,
			HttpServletRequest request
			) {
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = {UserType.Admin};
		CheckUser.checkUser(token, userTypes);
		
		return courseCodeService.deleteCourseCode(courseId, courseCodeId);
	}
	
	
	/**
	* @api {post} api/course/useCourseCode 使用激活码（用户）
	* @apiName course_courseCode_useCourseCode
	* @apiGroup course_courseCode
	* 
	* @apiHeader {String} token 身份凭证
	*
	* @apiParam {String} courseCodeStr * 课程激活码（必须）
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
	@RequestMapping(value = "/useCourseCode", method = RequestMethod.POST)
	@ResponseBody
	public DataWrapper<Void> useCourseCode(
			@RequestParam(value = "courseCodeStr", required = true) String courseCodeStr,
			HttpServletRequest request
			) {
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = {UserType.User};
		CheckUser.checkUser(token, userTypes);
		
		return courseCodeService.useCourseCode(courseCodeStr, token.getUserId());
	}
	
	/**
	* @api {post} api/course/isBought 是否已经购买课程（用户）
	* @apiName course_isBought
	* @apiGroup course
	* 
	* @apiHeader {String} token 身份凭证
	*
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "成功",
  	*		"data": true,
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
	@RequestMapping(value = "/isBought", method = RequestMethod.GET)
	@ResponseBody
	public DataWrapper<Boolean> isBought(
			HttpServletRequest request
			) {
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = {UserType.User};
		CheckUser.checkUser(token, userTypes);
		
		return courseCodeService.isBought(token.getUserId());
	}
	
	

}
