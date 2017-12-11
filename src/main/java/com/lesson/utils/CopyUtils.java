package com.lesson.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lesson.models.Course;
import com.lesson.models.CourseArrangement;
import com.lesson.models.CourseContent;

public class CopyUtils {
	public static Course copeCourse(Course course) {
		if (course == null) {
			return null;
		}
		Course newCourse = new Course();
		newCourse.setCourseId(null);
		newCourse.setName(course.getName());
		newCourse.setImgSrc(course.getImgSrc());
		newCourse.setIsFree(course.getIsFree());
		newCourse.setType(course.getType());
		newCourse.setCreateDate(new Date());
		newCourse.setState(course.getState());
		newCourse.setLink(course.getLink());
		return newCourse;
	}
	
	public static List<CourseArrangement> copyCourseArrangementList(List<CourseArrangement> courseArrangementList, 
			Long targetCourseId, Integer targetArrangementOrder) {
		if (courseArrangementList == null || courseArrangementList.size() == 0) {
			return null;
		}
		List<CourseArrangement> newCourseArrangementList = new ArrayList<CourseArrangement>();
		for(CourseArrangement toCopy : courseArrangementList) {
			
			CourseArrangement copied = copyCourseArrangement(toCopy, targetCourseId, targetArrangementOrder == null ? toCopy.getArrangementOrder() : targetArrangementOrder);
			if (copied != null) {
				newCourseArrangementList.add(copied);
			}
			
		}
		
		return newCourseArrangementList;
	}
	
	public static CourseArrangement copyCourseArrangement(CourseArrangement courseArrangement, Long targetCourseId, Integer targetArrangementOrder) {
		if (courseArrangement == null) {
			return null;
		}
		CourseArrangement newCourseArrangement = new CourseArrangement();
		newCourseArrangement.setCourseArrangementId(null);
		newCourseArrangement.setImgSrc(courseArrangement.getImgSrc());
		newCourseArrangement.setName(courseArrangement.getName());
		newCourseArrangement.setCourseId(targetCourseId);
		newCourseArrangement.setState(courseArrangement.getState());
		newCourseArrangement.setArrangementOrder(targetArrangementOrder);
		return newCourseArrangement;
		
	}
	
	public static List<CourseContent> copyCourseContentList(List<CourseContent> courseContentList,
			Long targetCourseId, Long targetCourseArrangementId, Integer targetContentOrder) {
		if (courseContentList == null || courseContentList.size() == 0) {
			return null;
		}
		
		List<CourseContent> newCourseContentList = new ArrayList<CourseContent>();
		for(CourseContent toCopy: courseContentList) {
			
			CourseContent copied = copyCourseContent(toCopy, targetCourseId, targetCourseArrangementId, targetContentOrder== null? toCopy.getContentOrder() : targetContentOrder);
			
			if (copied != null) {
				newCourseContentList.add(copied);
			}
		}
		
		
		return newCourseContentList;
		
	}
	
	
	public static CourseContent copyCourseContent(CourseContent courseContent, Long targetCourseId, Long targetCourseArrangementId, Integer targetContentOrder) {
		if (courseContent == null) {
			return null;
		}
		CourseContent newCourseContent = new CourseContent();
		newCourseContent.setCourseContentId(null);
		newCourseContent.setName(courseContent.getName());
		newCourseContent.setSubName(courseContent.getSubName());
		newCourseContent.setContent(courseContent.getContent());
		newCourseContent.setCourseId(targetCourseId);
		newCourseContent.setCourseArrangementId(targetCourseArrangementId);
		newCourseContent.setBookName(courseContent.getBookName());
		newCourseContent.setBookImgSrc(courseContent.getBookImgSrc());
		newCourseContent.setState(courseContent.getState());
		newCourseContent.setContentOrder(targetContentOrder);
		return newCourseContent;
		
	}

}
