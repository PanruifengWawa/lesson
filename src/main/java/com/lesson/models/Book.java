package com.lesson.models;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_book")
public class Book {
	private Long bookId;
	private String bookName;
	private String bookImgSrc;
	private Date readDate;
	private Long userId;
	private Integer isCourseBook;
	private Long courseContentId;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	
	@Basic
	@Column(name = "book_name")
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	
	@Basic
	@Column(name = "book_img_src")
	public String getBookImgSrc() {
		return bookImgSrc;
	}
	public void setBookImgSrc(String bookImgSrc) {
		this.bookImgSrc = bookImgSrc;
	}
	
	@Basic
	@Column(name = "read_date")
	public Date getReadDate() {
		return readDate;
	}
	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}
	
	@Basic
	@Column(name = "user_id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Basic
	@Column(name = "is_course_book")
	public Integer getIsCourseBook() {
		return isCourseBook;
	}
	public void setIsCourseBook(Integer isCourseBook) {
		this.isCourseBook = isCourseBook;
	}
	
	@Basic
	@Column(name = "course_content_id")
	public Long getCourseContentId() {
		return courseContentId;
	}
	public void setCourseContentId(Long courseContentId) {
		this.courseContentId = courseContentId;
	}
	
	

}
