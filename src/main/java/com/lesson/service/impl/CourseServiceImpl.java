package com.lesson.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lesson.enums.CourseFreeEnum;
import com.lesson.enums.CourseTypeEnum;
import com.lesson.exceptions.AuthException;
import com.lesson.exceptions.DBException;
import com.lesson.exceptions.ParameterException;
import com.lesson.models.Course;
import com.lesson.repository.CourseRepository;
import com.lesson.service.CourseService;
import com.lesson.utils.DataWrapper;

@Service
public class CourseServiceImpl implements CourseService {
	
	@Autowired
	CourseRepository courseRepository;

	@Override
	public DataWrapper<Void> addCourse(Course course) {
		// TODO Auto-generated method stub
		if (course.getName() == null || course.getName().equals("")) {
			throw new ParameterException("课程名为空");
		}
		if (course.getImgSrc() == null || course.getImgSrc().equals("")) {
			throw new ParameterException("课程封面为空");
		}
		if (course.getIsFree() == null || CourseFreeEnum.parse(course.getIsFree()) == null) {
			throw new ParameterException("收费类型错误");
		}
		
		if (course.getType() == null || CourseTypeEnum.parse(course.getType()) == null) {
			throw new ParameterException("课程类型错误");
		}
		
		course.setCourseId(null);
		course.setCreateDate(new Date());
		try {
			courseRepository.save(course);
		} catch (Exception e) {
			// TODO: handle exception
			throw new DBException("数据库错误",e);
		}
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		return dataWrapper;
	}

	@Override
	public DataWrapper<List<Course>> getCourseList(Integer isFree, Integer type, Integer numberPerPage,
			Integer currentPage) {
		// TODO Auto-generated method stub
		return courseRepository.getCourseList(isFree, type, numberPerPage, currentPage);
	}

	@Override
	public DataWrapper<List<Course>> getFreeCourseList(Integer type, Integer numberPerPage, Integer currentPage) {
		// TODO Auto-generated method stub
		return courseRepository.getCourseList(CourseFreeEnum.Yes.getCode(), type, numberPerPage, currentPage);
	}

	@Override
	public DataWrapper<Void> updateCourse(Course course) {
		// TODO Auto-generated method stub
		Course courseInDB = courseRepository.findByCourseId(course.getCourseId());
		if (courseInDB == null) {
			throw new AuthException("课程不存在");
		}
		if (course.getName() != null && !course.getName().equals("")) {
			courseInDB.setName(course.getName());
		}
		if (course.getImgSrc() != null && !course.getImgSrc().equals("")) {
			courseInDB.setImgSrc(course.getImgSrc());
		}
		
		if (course.getIsFree() != null && CourseFreeEnum.parse(course.getIsFree()) != null) {
			courseInDB.setIsFree(course.getIsFree());
		}
		if (course.getType() != null && CourseTypeEnum.parse(course.getType()) != null) {
			courseInDB.setType(course.getType());
		}
		if (course.getLink() != null && !course.getLink().equals("")) {
			courseInDB.setLink(course.getLink());
		}
		
		try {
			courseRepository.save(courseInDB);
		} catch (Exception e) {
			// TODO: handle exception
			throw new DBException("数据库错误",e);
		}
		
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> deleteCourse(Long courseId) {
		// TODO Auto-generated method stub
		Course course = courseRepository.findByCourseId(courseId);
		if (course == null) {
			throw new AuthException("课程不存在");
		}
		try {
			courseRepository.delete(course);
		} catch (Exception e) {
			// TODO: handle exception
			throw new DBException("数据库错误",e);
		}
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		return dataWrapper;
	}

	@Override
	public DataWrapper<List<Course>> getCourseList(Integer type, Integer numberPerPage, Integer currentPage) {
		// TODO Auto-generated method stub
		return courseRepository.getCourseListFromUser(type, numberPerPage, currentPage);
	}

}
