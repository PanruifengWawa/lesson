package com.lesson.service.impl;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lesson.enums.CourseCodeStateNums;
import com.lesson.exceptions.AuthException;
import com.lesson.exceptions.DBException;
import com.lesson.exceptions.ParameterException;
import com.lesson.models.Course;
import com.lesson.models.CourseCode;
import com.lesson.repository.CourseCodeRepository;
import com.lesson.repository.CourseRepository;
import com.lesson.service.CourseCodeService;
import com.lesson.utils.DataWrapper;
import com.lesson.utils.UUIDGenerator;

@Service
public class CourseCodeServiceImpl implements CourseCodeService {
	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	CourseCodeRepository courseCodeRepository;

	@Override
	public DataWrapper<List<String>> addCourseCode(Long courseId, Long userId, Integer num) {
		// TODO Auto-generated method stub
		if (num == null || num <=0 || num > 100) {
			throw new ParameterException("生成的数量错误");
		}
		Course course = courseRepository.findByCourseId(courseId);
		if (course == null) {
			throw new AuthException("课程不存在");
		}
		List<String> list = new ArrayList<String>();
		for(int i = 0 ; i < num ;) {
			String courseCodeStr = UUIDGenerator.getCode().replaceAll("-", "");
			CourseCode courseCode = new CourseCode();
			courseCode.setCourseCodeId(null);
			courseCode.setCourseCodeStr(courseCodeStr);
			courseCode.setCourseId(courseId);
			courseCode.setUserId(userId);
			courseCode.setIsUsed(CourseCodeStateNums.No.getCode());
			courseCode.setUseDate(null);
			try {
				courseCodeRepository.save(courseCode);
				list.add(courseCode.getCourseCodeStr());
				i++;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
		DataWrapper<List<String>> dataWrapper = new DataWrapper<List<String>>();
		dataWrapper.setData(list);
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> deleteCourseCode(Long courseId, Long courseCodeId) {
		// TODO Auto-generated method stub
		CourseCode courseCode = courseCodeRepository.findByCourseCodeId(courseCodeId);
		if (courseCode == null) {
			throw new AuthException("课程激活码不存在");
		}
		if (!courseCode.getCourseId().equals(courseId)) {
			throw new AuthException("课程激活码不属于该课程");
		}
		try {
			courseCodeRepository.delete(courseCode);
		} catch (Exception e) {
			// TODO: handle exception
			throw new DBException("数据库错误",e);
		}
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		return dataWrapper;
	}

	@Override
	public DataWrapper<List<CourseCode>> getCourseCodeList(Long courseId, Integer numberPerPage, Integer currentPage) {
		// TODO Auto-generated method stub
		if (courseId == null) {
			throw new ParameterException("请选择课程");
		}
		
	

		if (numberPerPage == null || numberPerPage <=0 || numberPerPage > 10) {
			numberPerPage = 10;
		}
		if (currentPage == null || currentPage <=0) {
			currentPage = 1;
		}
		
		Pageable pageable = new PageRequest(currentPage-1, numberPerPage);
		Page<CourseCode> page = courseCodeRepository.findCodeListByCourseId(courseId, pageable);
		
		DataWrapper<List<CourseCode>> dataWrapper = new DataWrapper<List<CourseCode>>();

		dataWrapper.setData(page.getContent());
        dataWrapper.setTotalNumber((int)page.getTotalElements());
        dataWrapper.setCurrentPage(currentPage);
        dataWrapper.setTotalPage(page.getTotalPages());
        dataWrapper.setNumberPerPage(numberPerPage);
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> useCourseCode(String courseCodeStr, Long userId) {
		// TODO Auto-generated method stub
		CourseCode courseCode = courseCodeRepository.findByCourseCodeStr(courseCodeStr);
		if (courseCode == null) {
			throw new AuthException("课程激活码不存在");
		}
		if (courseCode.getIsUsed().equals(CourseCodeStateNums.Yes.getCode())) {
			throw new AuthException("该课程激活码已被使用，无法再次使用");
		}
		if (courseCodeRepository.findByUserIdAndCourseId(userId, courseCode.getCourseId()) != null) {
			throw new AuthException("您已激活此课程，无需再次激活");
		}
		courseCode.setUserId(userId);
		courseCode.setIsUsed(CourseCodeStateNums.Yes.getCode());
		courseCode.setUseDate(new Date());
		try {
			courseCodeRepository.save(courseCode);
		} catch (Exception e) {
			// TODO: handle exception
			throw new DBException("数据库错误",e);
		}
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		return dataWrapper;
	}

}
