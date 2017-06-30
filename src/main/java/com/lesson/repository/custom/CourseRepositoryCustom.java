package com.lesson.repository.custom;

import java.util.List;

import com.lesson.models.Course;
import com.lesson.utils.DataWrapper;

public interface CourseRepositoryCustom {
	//管理员获取课程列表或者免费课
	DataWrapper<List<Course>> getCourseList(Integer isFree, Integer type, Integer numberPerPage,
			Integer currentPage);
	
	//用户获取课程列表(所有的课程),用isBought区分是否已经购买
	DataWrapper<List<Course>> getCourseListFromUser(Long userId, Integer type, Integer numberPerPage,
			Integer currentPage);

	//管理员获取用户已激活的课程
	DataWrapper<List<Course>> getCourseListByUserId(Long userId, Integer numberPerPage,
			Integer currentPage, Integer totalltemNum);
}
