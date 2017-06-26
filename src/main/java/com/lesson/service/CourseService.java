package com.lesson.service;

import java.util.List;

import com.lesson.models.Course;
import com.lesson.utils.DataWrapper;

public interface CourseService {
	DataWrapper<Void> addCourse(Course course);
	
	DataWrapper<List<Course>> getCourseList(Integer isFree, Integer type, Integer numberPerPage, Integer currentPage);
	
	DataWrapper<List<Course>> getFreeCourseList(Integer type, Integer numberPerPage, Integer currentPage);
	
	DataWrapper<Void> updateCourse(Course course);
	
	DataWrapper<Void> deleteCourse(Long courseId);
	
	DataWrapper<List<Course>> getCourseList(Integer type, Integer numberPerPage, Integer currentPage);
	
	

}
