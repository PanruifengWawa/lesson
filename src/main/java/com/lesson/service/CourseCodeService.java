package com.lesson.service;

import java.util.List;

import com.lesson.models.CourseCode;
import com.lesson.utils.DataWrapper;

public interface CourseCodeService {
	DataWrapper<List<String>> addCourseCode(Long courseId, Long userId, Integer num);
	
	DataWrapper<Void> deleteCourseCode(Long courseId, Long courseCodeId);
	
	DataWrapper<List<CourseCode>> getCourseCodeList(Long courseId, Integer numberPerPage, Integer currentPage);
	
	DataWrapper<Void> useCourseCode(String courseCodeStr, Long userId);
	
	DataWrapper<Boolean> isBought(Long userId);
}
