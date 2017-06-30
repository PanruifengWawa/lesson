package com.lesson.service;

import java.util.List;

import com.lesson.models.Book;
import com.lesson.utils.DataWrapper;

public interface BookService {
	DataWrapper<Book> addBook(Book book);
	DataWrapper<List<Book>> getUserBookListByUserId(Long userId, Integer numberPerPage, Integer currentPage);
	DataWrapper<Void> deleteBook(Long userId, Long bookId);

}
