package com.lesson.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lesson.enums.BookOrigin;
import com.lesson.exceptions.AuthException;
import com.lesson.exceptions.DBException;
import com.lesson.exceptions.ParameterException;
import com.lesson.models.Book;
import com.lesson.repository.BookRepository;
import com.lesson.service.BookService;
import com.lesson.utils.DataWrapper;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	BookRepository bookRepository;

	@Override
	public DataWrapper<List<Book>> getUserBookListByUserId(Long userId, Integer numberPerPage, Integer currentPage) {
		// TODO Auto-generated method stub
		if (numberPerPage == null || numberPerPage <= 0 || numberPerPage > 10) {
			numberPerPage = 10;
		}
		if (currentPage == null || currentPage <= 0) {
			currentPage = 1;
		}
		
		Pageable pageable = new PageRequest(currentPage-1, numberPerPage);
		
		Page<Book> page = bookRepository.findBookListByUserId(userId, pageable);
		
		DataWrapper<List<Book>> dataWrapper = new DataWrapper<List<Book>>();
		dataWrapper.setData(page.getContent());
        dataWrapper.setTotalNumber((int)page.getTotalElements());
        dataWrapper.setCurrentPage(currentPage);
        dataWrapper.setTotalPage(page.getTotalPages());
        dataWrapper.setNumberPerPage(numberPerPage);
		return dataWrapper;
	}

	@Override
	public DataWrapper<Book> addBook(Book book) {
		// TODO Auto-generated method stub
		if (book.getBookName() == null || book.getBookName().equals("")) {
			throw new ParameterException("绘本名称为空");
		}
		
		if (book.getBookImgSrc() == null || book.getBookImgSrc().equals("")) {
			throw new ParameterException("绘本封面为空");
		}
		if (book.getUserId() == null) {
			throw new ParameterException("用户未登录");
		}
		book.setBookId(null);
		book.setReadDate(new Date());
		book.setIsCourseBook(BookOrigin.PersonalBook.getCode());
		book.setCourseContentId(null);
		try {
			bookRepository.save(book);
		} catch (Exception e) {
			// TODO: handle exception
			throw new DBException("数据库错误",e);
		}
		DataWrapper<Book> dataWrapper = new DataWrapper<Book>();
		dataWrapper.setData(book);
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> deleteBook(Long userId, Long bookId) {
		// TODO Auto-generated method stub
		Book book = bookRepository.findByBookId(bookId);
		if (book == null) {
			throw new AuthException("绘本不存在");
		}
		if (!book.getUserId().equals(userId)) {
			throw new AuthException("无权删不属于自己的书籍");
		}
		if (book.getIsCourseBook().equals(BookOrigin.CourseBook.getCode())) {
			throw new AuthException("无权删除课程书籍");
		}
		try {
			bookRepository.delete(book);
		} catch (Exception e) {
			// TODO: handle exception
			throw new DBException("数据库错误",e);
		}
		
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		return dataWrapper;
	}

}
