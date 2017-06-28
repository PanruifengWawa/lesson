package com.lesson.service;

import java.util.List;

import com.lesson.models.Course;
import com.lesson.models.CourseArrangement;
import com.lesson.models.CourseContent;
import com.lesson.models.Token;
import com.lesson.utils.DataWrapper;

public interface CourseService {
	//course
	DataWrapper<Course> addCourse(Course course);
	
	DataWrapper<List<Course>> getCourseList(Integer isFree, Integer type, Integer numberPerPage, Integer currentPage);
	
	DataWrapper<List<Course>> getFreeCourseList(Integer type, Integer numberPerPage, Integer currentPage);
	
	DataWrapper<Void> updateCourse(Course course);
	
	DataWrapper<Void> deleteCourse(Long courseId);
	
	DataWrapper<List<Course>> getCourseList(Integer type, Integer numberPerPage, Integer currentPage);
	
	
	//course arrangement
	DataWrapper<CourseArrangement> addArrangeMent(CourseArrangement courseArrangement);
	
	DataWrapper<List<CourseArrangement>> getArrangeMentList(Long courseId, Token token);
	
	DataWrapper<Void> updateCourseArrangement(Long courseArrangementId, CourseArrangement courseArrangement);
	
	DataWrapper<Void> deleteCourseArrangement(Long courseArrangementId);
	
	//course content
	DataWrapper<CourseContent> addCourseContent(CourseContent courseContent);
	
	DataWrapper<Void> deleteCourseContent(Long courseContentId);
	
	DataWrapper<Void> updateCourseContent(CourseContent courseContent);
	
	DataWrapper<CourseContent> getCourseContentDetails(Long courseId, Long courseContentId, Token token);

}
