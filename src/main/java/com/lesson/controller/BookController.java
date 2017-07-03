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
import com.lesson.models.Book;
import com.lesson.models.Token;
import com.lesson.repository.TokenRepository;
import com.lesson.service.BookService;
import com.lesson.utils.CheckUser;
import com.lesson.utils.DataWrapper;

@Controller
@RequestMapping(value = "/api/book")
public class BookController {
	
	@Autowired
	BookService bookService;
	
	@Autowired
	TokenRepository tokenRepository;
	
	/**
	* @api {post} api/book 添加书籍（用户）
	* @apiName book_add
	* @apiGroup book
	* 
	* @apiHeader {String} token 身份凭证
	* @apiParam {String} bookName * 书籍名（必须）
	* @apiParam {String} bookImgSrc * 书籍封面（必须）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "成功",
  	*		"data": {
    *			"bookId": 1,
    *			"bookName": "测试书籍1",
    *			"bookImgSrc": "http://www.baidu.com",
    *			"readDate": 1498663078000,
    *			"userId": 3,
    *			"isCourseBook": 0,//0-个人书籍,1-课程书籍
    *			"courseContentId": null //自己添加的书籍则为空
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
	public DataWrapper<Book> addBook(
			@ModelAttribute Book book,
			HttpServletRequest request
			) {
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = { UserType.User};
		CheckUser.checkUser(token, userTypes);
		book.setUserId(token.getUserId());
		
		return bookService.addBook(book);
	}
	
	
	/**
	* @api {get} api/book 获取书籍列表（用户）
	* @apiName book_get_book_list
	* @apiGroup book
	*
	* @apiHeader {String} token 身份凭证
	* @apiParam {int} numberPerPage * 每页大小（非必须,默认分页,1-10）//这是由于阅读的书籍可能过多,像1000本
	* @apiParam {int} currentPage * 当前页（非必须）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "成功",
  	*		"data": [{
    *			"bookId": 1,
    *			"bookName": "测试书籍1",
    *			"bookImgSrc": "http://www.baidu.com",
    *			"readDate": 1498663078000,
    *			"userId": 3,
    *			"isCourseBook": 1,//0-个人书籍,1-课程书籍
    *			"courseContentId": 2
    *		}],
  	*		"token": null,
  	*		"numberPerPage": 10,
	*  		"currentPage": 1,
	*  		"totalNumber": 100,
	*  		"totalPage": 10
	*	}
	*
	* @apiSuccessExample {json} Error-Response:
	* 	HTTP/1.1 200 ok
	* 	{
	*  		"callStatus": "FAILED",
	*  		"errorCode": "权限错误",
	*  		"data": "用户未登录",
	*  		"token": null
	*  		"numberPerPage": 10,
	*  		"currentPage": 1,
	*  		"totalNumber": 1,
	*  		"totalPage": 1
	*	}
	**/
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public DataWrapper<List<Book>> getBookList(
			@RequestParam(value = "numberPerPage", required = false) Integer numberPerPage,
    		@RequestParam(value = "currentPage", required = false) Integer currentPage,
			HttpServletRequest request
			) {
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = { UserType.User};
		CheckUser.checkUser(token, userTypes);
		
		return bookService.getUserBookListByUserId(token.getUserId(), numberPerPage, currentPage);
	}
	
	/**
	* @api {delete} api/book/{bookId} 删除书籍（用户,只能删除自己手动添加的）
	* @apiName book_delete_book
	* @apiGroup book
	* 
	* @apiHeader {String} token 身份凭证
	* @apiParam {Long} bookId * 书籍id
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
	@RequestMapping(value = "{bookId}", method = RequestMethod.DELETE)
	@ResponseBody
	public DataWrapper<Void> deleteBook(
			@PathVariable Long bookId,
			HttpServletRequest request
			) {
		Token token = tokenRepository.findByTokenStr(request.getHeader("token"));
		UserType[] userTypes = { UserType.User};
		CheckUser.checkUser(token, userTypes);
		
		return bookService.deleteBook(token.getUserId(), bookId);
	}
	
	
	
	/**
	* @api {get} api/book/share 书籍分享
	* @apiName book_share
	* @apiGroup book
	* 
	* @apiParam {Long} userId * 用户id
	* @apiParam {int} numberPerPage * 每页大小（必须,默认分页,1-10）//防止有人直接调用接口拖库
	* @apiParam {int} currentPage * 当前页（必须）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "成功",
  	*		"data": [{
    *			"bookId": 1,
    *			"bookName": "测试书籍1",
    *			"bookImgSrc": "http://www.baidu.com",
    *			"readDate": 1498663078000,
    *			"userId": 3,
    *			"isCourseBook": 1,//0-个人书籍,1-课程书籍
    *			"courseContentId": 2
    *		}],
  	*		"token": null,
  	*		"numberPerPage": 10,
	*  		"currentPage": 1,
	*  		"totalNumber": 100,
	*  		"totalPage": 10
	*	}
	*
	* @apiSuccessExample {json} Error-Response:
	* 	HTTP/1.1 200 ok
	* 	{
	*  		"callStatus": "FAILED",
	*  		"errorCode": "权限错误",
	*  		"data": "用户未登录",
	*  		"token": null
	*  		"numberPerPage": 10,
	*  		"currentPage": 1,
	*  		"totalNumber": 1,
	*  		"totalPage": 1
	*	}
	**/
	@RequestMapping(value = "share", method = RequestMethod.GET)
	@ResponseBody
	public DataWrapper<List<Book>> getUserBookList(
			@RequestParam(value = "userId", required = true) Long userId,
			@RequestParam(value = "numberPerPage", required = true) Integer numberPerPage,
    		@RequestParam(value = "currentPage", required = true) Integer currentPage,
			HttpServletRequest request
			) {
		return bookService.getUserBookListByUserId(userId, numberPerPage, currentPage);
	}

}
