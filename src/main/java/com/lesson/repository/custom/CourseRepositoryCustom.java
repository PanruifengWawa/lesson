package com.lesson.repository.custom;

import java.util.List;

import com.lesson.models.Course;
import com.lesson.utils.DataWrapper;

public interface CourseRepositoryCustom {
	DataWrapper<List<Course>> getCourseList(Integer isFree, Integer type, Integer numberPerPage,
			Integer currentPage);
	
	DataWrapper<List<Course>> getCourseListFromUser(Integer type, Integer numberPerPage,
			Integer currentPage);

}
