package com.lesson.models;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;


@Entity
@Table(name = "t_course_arrangement")
public class CourseArrangement {
	private Long courseArrangementId;
	private String imgSrc;
	private String name;
	private Long courseId;
	private Integer state;
	private Integer arrangementOrder;
	private List<CourseContentSub> courseContent;
	
	
	
	@Basic
	@Column(name = "arrangement_order")
	public Integer getArrangementOrder() {
		return arrangementOrder;
	}
	public void setArrangementOrder(Integer arrangementOrder) {
		this.arrangementOrder = arrangementOrder;
	}
	@Basic
	@Column(name = "state")
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_arrangement_id")
	public Long getCourseArrangementId() {
		return courseArrangementId;
	}
	public void setCourseArrangementId(Long courseArrangementId) {
		this.courseArrangementId = courseArrangementId;
	}
	
	@Basic
	@Column(name = "img_src")
	public String getImgSrc() {
		return imgSrc;
	}
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	
	@Basic
	@Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Basic
	@Column(name = "course_id")
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	
	@OneToMany
	@JoinColumn(name="course_arrangement_id")
	@OrderBy(value = "content_order asc,course_content_id asc")  
	public List<CourseContentSub> getCourseContent() {
		return courseContent;
	}
	public void setCourseContent(List<CourseContentSub> courseContent) {
		this.courseContent = courseContent;
	}
	
	
	
}
