package com.lesson.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lesson.models.Course;
import com.lesson.models.CourseArrangement;
import com.lesson.models.CourseContent;
import com.lesson.models.Token;
import com.lesson.utils.DataWrapper;

public interface CourseService {
	//course
	DataWrapper<Course> addCourse(Course course);
	
	DataWrapper<Void> changeState(Long courseId, Integer state);
	
	DataWrapper<Course> getCourseDetails(Long courseId);
	
	//(管理员)获取课程列表(所有)
	DataWrapper<List<Course>> getCourseList(Integer isFree, Integer type, Integer numberPerPage, Integer currentPage);
	
	//获取体验课列表
	DataWrapper<List<Course>> getFreeCourseList(Integer type, Integer numberPerPage, Integer currentPage);
	
	DataWrapper<Void> updateCourse(Course course);
	
	DataWrapper<Void> deleteCourse(Long courseId);
	
	//(用户)获取课程列表(所有),用isBought区分是否已购买
	DataWrapper<List<Course>> getCourseListFromUser(Long userId, Integer type, Integer numberPerPage, Integer currentPage);
	
	//(管理员)获取某个用户已经购买的课程(非所有)
	DataWrapper<List<Course>> getUserCourseListByUserId(Long userId, Integer numberPerPage, Integer currentPage);
	
	
	//course arrangement
	DataWrapper<CourseArrangement> addArrangeMent(CourseArrangement courseArrangement);
	
	DataWrapper<CourseArrangement> getArrangeMentDetails(Long courseArrangementId);
	
	DataWrapper<List<CourseArrangement>> getArrangeMentList(Long courseId, Token token);
	
	DataWrapper<Void> updateCourseArrangement(Long courseArrangementId, CourseArrangement courseArrangement);
	
	DataWrapper<Void> deleteCourseArrangement(Long courseArrangementId);
	
	DataWrapper<Void> changeArrangementState(Long courseArrangementId, Integer state);
	
	//course content
	DataWrapper<CourseContent> addCourseContent(CourseContent courseContent);
	
	DataWrapper<Void> deleteCourseContent(Long courseContentId);
	
	DataWrapper<Void> updateCourseContent(CourseContent courseContent);
	
	DataWrapper<CourseContent> getCourseContentDetails(Long courseId, Long courseContentId, Token token);
	
	DataWrapper<Void> changeContentState(Long courseContentId, Integer state);
	
	@Transactional
	DataWrapper<Void> copyCourse(Long courseId);

}
