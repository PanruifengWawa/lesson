package com.lesson.models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_course_content")
public class CourseContentSub {
	private Long courseContentId;
	private String name;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_content_id")
	public Long getCourseContentId() {
		return courseContentId;
	}
	public void setCourseContentId(Long courseContentId) {
		this.courseContentId = courseContentId;
	}
	
	@Basic
	@Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
