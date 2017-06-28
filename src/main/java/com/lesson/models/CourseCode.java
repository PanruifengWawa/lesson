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
@Table(name = "t_course_code")
public class CourseCode {
	private Long courseCodeId;
	private String courseCodeStr;
	private Long courseId;
	private Long userId;
	private Integer isUsed;
	private Date useDate;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_code_id")
	public Long getCourseCodeId() {
		return courseCodeId;
	}
	public void setCourseCodeId(Long courseCodeId) {
		this.courseCodeId = courseCodeId;
	}
	
	
	@Basic
	@Column(name = "course_code_str")
	public String getCourseCodeStr() {
		return courseCodeStr;
	}
	public void setCourseCodeStr(String courseCodeStr) {
		this.courseCodeStr = courseCodeStr;
	}
	
	@Basic
	@Column(name = "course_id")
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
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
	@Column(name = "is_used")
	public Integer getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}
	
	@Basic
	@Column(name = "use_date")
	public Date getUseDate() {
		return useDate;
	}
	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}
	
	
	

}
