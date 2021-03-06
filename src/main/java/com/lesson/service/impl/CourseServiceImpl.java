package com.lesson.service.impl;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.lesson.enums.BookOrigin;
import com.lesson.enums.CourseFreeEnum;
import com.lesson.enums.CourseStateEnum;
import com.lesson.enums.CourseTypeEnum;
import com.lesson.enums.UserType;
import com.lesson.exceptions.AuthException;
import com.lesson.exceptions.DBException;
import com.lesson.exceptions.ParameterException;
import com.lesson.models.Book;
import com.lesson.models.Course;
import com.lesson.models.CourseArrangement;
import com.lesson.models.CourseContent;
import com.lesson.models.Token;
import com.lesson.repository.BookRepository;
import com.lesson.repository.CourseArrangementRepository;
import com.lesson.repository.CourseCodeRepository;
import com.lesson.repository.CourseContentRepository;
import com.lesson.repository.CourseRepository;
import com.lesson.service.CourseService;
import com.lesson.utils.CopyUtils;
import com.lesson.utils.DataWrapper;

@Service
public class CourseServiceImpl implements CourseService {
	
	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	CourseArrangementRepository courseArrangementRepository;
	
	@Autowired
	CourseCodeRepository courseCodeRepository;
	
	@Autowired
	CourseContentRepository courseContentRepository;
	
	@Autowired
	BookRepository bookRepository;

	@Override
	public DataWrapper<Course> addCourse(Course course) {
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
		course.setState(CourseStateEnum.Close.getCode());
		try {
			courseRepository.save(course);
		} catch (Exception e) {
			// TODO: handle exception
			throw new DBException("数据库错误",e);
		}
		DataWrapper<Course> dataWrapper = new DataWrapper<Course>();
		dataWrapper.setData(course);
		return dataWrapper;
	}

	@Override
	public DataWrapper<List<Course>> getCourseList(Integer isFree, Integer type, Integer numberPerPage,
			Integer currentPage) {
		// TODO Auto-generated method stub
		return courseRepository.getCourseList(null, isFree, type, numberPerPage, currentPage);
	}

	@Override
	public DataWrapper<List<Course>> getFreeCourseList(Integer type, Integer numberPerPage, Integer currentPage) {
		// TODO Auto-generated method stub
		return courseRepository.getCourseList(CourseStateEnum.Open.getCode(), CourseFreeEnum.Yes.getCode(), type, numberPerPage, currentPage);
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
	public DataWrapper<List<Course>> getCourseListFromUser(Long userId, Integer type, Integer numberPerPage, Integer currentPage) {
		// TODO Auto-generated method stub
		if (type == null || CourseTypeEnum.parse(type) == null) {
			throw new ParameterException("课程类型错误");
		}
		return courseRepository.getCourseListFromUser(userId, type, numberPerPage, currentPage);
	}

	@Override
	public DataWrapper<CourseArrangement> addArrangeMent(CourseArrangement courseArrangement) {
		// TODO Auto-generated method stub
		Course course = courseRepository.findByCourseId(courseArrangement.getCourseId());
		if (course == null) {
			throw new AuthException("课程不存在");
		}
		if (courseArrangement.getImgSrc() == null || courseArrangement.getImgSrc().equals("")) {
			throw new ParameterException("课程安排封面为空");
		}
		if (courseArrangement.getName() == null || courseArrangement.getName().equals("")) {
			throw new ParameterException("课程安排名称为空");
		}
		
		if (courseArrangement.getArrangementOrder() == null) {
			courseArrangement.setArrangementOrder(0);
		}
		
		courseArrangement.setCourseArrangementId(null);
		courseArrangement.setCourseContent(null);
		courseArrangement.setState(0);
		
		try {
			courseArrangementRepository.save(courseArrangement);
		} catch (Exception e) {
			// TODO: handle exception
			throw new DBException("数据库错误",e);
		}
		DataWrapper<CourseArrangement> dataWrapper = new DataWrapper<CourseArrangement>();
		dataWrapper.setData(courseArrangement);
		return dataWrapper;
	}

	//管理员,已经购买课程的用户,体验课
	@Override
	public DataWrapper<List<CourseArrangement>> getArrangeMentList(Long courseId, Token token) {
		// TODO Auto-generated method stub
		Course course = courseRepository.findByCourseId(courseId);
		if (course == null) {
			throw new AuthException("课程不存在");
		}

		if (course.getState().equals(CourseStateEnum.Close.getCode()) && (token == null || token.getUserType().equals(UserType.User.getCode()) )) {
			throw new AuthException("课程未开放");
		}
		
		DataWrapper<List<CourseArrangement>> dataWrapper = new DataWrapper<List<CourseArrangement>>();
		Page<CourseArrangement> page = null;
		
		if (course.getIsFree().equals(CourseFreeEnum.Yes.getCode())) {
			page = courseArrangementRepository.findArrangementListByCourseId(courseId, null);
//			dataWrapper.setData(page.getContent());
		} else {
			if (token != null) {
				if (token.getUserType().equals(UserType.Admin.getCode()) || courseCodeRepository.findByUserIdAndCourseId(token.getUserId(), courseId) != null ) {
					page = courseArrangementRepository.findArrangementListByCourseId(courseId, null);
//					dataWrapper.setData(page.getContent());
				} else {
					throw new AuthException("未激活此课程");
				}
			} else {
				throw new AuthException("用户未登录");
			}
		}
		
		
		if (page != null) {
			if ( token == null || token.getUserType().equals(UserType.User.getCode()) ) {
				List<CourseArrangement> courseArrangements = new ArrayList<CourseArrangement>();
				for(CourseArrangement courseArrangement : page.getContent()) {
					if (courseArrangement.getState().equals(CourseStateEnum.Open.getCode())) {
						courseArrangements.add(courseArrangement);
					}
				}

				dataWrapper.setData(courseArrangements);
			} else {
				dataWrapper.setData(page.getContent());
			}
		}
		
	
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> updateCourseArrangement(Long courseArrangementId, CourseArrangement courseArrangement) {
		// TODO Auto-generated method stub
		CourseArrangement courseArrangementInDB = courseArrangementRepository.findByCourseArrangementId(courseArrangementId);
		if (courseArrangementInDB == null) {
			throw new AuthException("课程安排不存在");
		}
		
		if (courseArrangement.getImgSrc() != null && !courseArrangement.getImgSrc().equals("")) {
			courseArrangementInDB.setImgSrc(courseArrangement.getImgSrc());
		}
		if (courseArrangement.getName() != null && !courseArrangement.getName().equals("")) {
			courseArrangementInDB.setName(courseArrangement.getName());
		}
		if (courseArrangement.getArrangementOrder() != null) {
			courseArrangementInDB.setArrangementOrder(courseArrangement.getArrangementOrder());
		}
		
		try {
			courseArrangementRepository.save(courseArrangementInDB);
		} catch (Exception e) {
			// TODO: handle exception
			throw new DBException("数据库错误",e);
		}
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> deleteCourseArrangement(Long courseArrangementId) {
		// TODO Auto-generated method stub
		try {
			courseArrangementRepository.deleteByCourseArrangementId(courseArrangementId);
		} catch (Exception e) {
			// TODO: handle exception
			throw new DBException("数据库错误",e);
		}
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		return dataWrapper;
	}

	@Override
	public DataWrapper<CourseContent> addCourseContent(CourseContent courseContent) {
		// TODO Auto-generated method stub
		if (courseContent.getName() == null || courseContent.getName().equals("")) {
			throw new ParameterException("课程内容标题为空");
		}
//		if (courseContent.getSubName() == null || courseContent.getSubName().equals("")) {
//			throw new ParameterException("课程内容副标题为空");
//		}
		if (courseContent.getSubName() == null) {
			courseContent.setSubName("");
		}
		
				
		if (courseContent.getContent() == null || courseContent.getContent().equals("")) {
			throw new ParameterException("课程内容为空");
		}
		if (courseContent.getCourseId() == null) {
			throw new ParameterException("请选择课程");
		}
		
		if (courseContent.getCourseArrangementId() == null) {
			throw new ParameterException("请选择课程安排");
		}
		if (courseContent.getBookName() == null ||courseContent.getBookName().equals("") ) {
			throw new ParameterException("课程内容书籍为空");
		}
		if (courseContent.getBookImgSrc() == null || courseContent.getBookImgSrc().equals("")) {
			throw new ParameterException("课程内容书籍封面为空");
		}
		Course course = courseRepository.findByCourseId(courseContent.getCourseId());
		if (course == null) {
			throw new AuthException("课程不存在");
		}
		
		
		if (courseContent.getContentOrder() == null) {
			courseContent.setContentOrder(0);
		}
		courseContent.setCourseContentId(null);
		courseContent.setState(0);
		try {
			courseContentRepository.save(courseContent);
		} catch (Exception e) {
			// TODO: handle exception
			throw new DBException("数据库错误",e);
		}
		DataWrapper<CourseContent> dataWrapper = new DataWrapper<CourseContent>();
		dataWrapper.setData(courseContent);
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> deleteCourseContent(Long courseContentId) {
		// TODO Auto-generated method stub
		try {
			courseContentRepository.deleteByCourseContentId(courseContentId);
		} catch (Exception e) {
			// TODO: handle exception
			throw new DBException("数据库错误",e);
		}
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> updateCourseContent(CourseContent courseContent) {
		// TODO Auto-generated method stub
		CourseContent courseContentInDB = courseContentRepository.findByCourseContentId(courseContent.getCourseContentId());
		if (courseContentInDB == null) {
			throw new AuthException("课程内容不存在");
		}
		if (courseContent.getName() != null && !courseContent.getName().equals("")) {
			courseContentInDB.setName(courseContent.getName());
		}
		if (courseContent.getSubName() != null && !courseContent.getSubName().equals("")) {
			courseContentInDB.setSubName(courseContent.getSubName());
		}
		if (courseContent.getContent() != null && !courseContent.getContent().equals("")) {
			courseContentInDB.setContent(courseContent.getContent());
		}
		if (courseContent.getContentOrder() != null) {
			courseContentInDB.setContentOrder(courseContent.getContentOrder());
		}
		try {
			courseContentRepository.save(courseContentInDB);
		} catch (Exception e) {
			// TODO: handle exception
			throw new DBException("数据库错误",e);
		}
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		return dataWrapper;
	}

	//管理员,已经购买课程的用户,体验课
	@Override
	public DataWrapper<CourseContent> getCourseContentDetails(Long courseId, Long courseContentId, Token token) {
		// TODO Auto-generated method stub		
		Course course = courseRepository.findByCourseId(courseId);
		if (course == null) {
			throw new AuthException("课程不存在");
		}
		
		if (course.getState().equals(CourseStateEnum.Close.getCode()) && (token == null || token.getUserType().equals(UserType.User.getCode()) )) {
			throw new AuthException("课程未开放");
		}
		
		DataWrapper<CourseContent> dataWrapper = new DataWrapper<CourseContent>();
		
		if (course.getIsFree().equals(CourseFreeEnum.Yes.getCode())) {
			CourseContent courseContent = courseContentRepository.findByCourseContentId(courseContentId);
			if (courseContent == null) {
				throw new AuthException("课程内容不存在");
			}
			if (!courseContent.getCourseId().equals(course.getCourseId())) {
				throw new AuthException("课程内容不属于该课程");
			}
			if (courseContent.getState().equals(CourseStateEnum.Close.getCode()) && (token == null || token.getUserType().equals(UserType.User.getCode())) ) {
				throw new AuthException("课程内容未开放");
			}
			dataWrapper.setData(courseContent);
		} else {
			if (token != null) {
				
				if (token.getUserType().equals(UserType.Admin.getCode())) { //admin
					CourseContent courseContent = courseContentRepository.findByCourseContentId(courseContentId);
					if (courseContent == null) {
						throw new AuthException("课程内容不存在");
					}
					if (!courseContent.getCourseId().equals(course.getCourseId())) {
						throw new AuthException("课程内容不属于该课程");
					}
					
					dataWrapper.setData(courseContent);
				} else if (courseCodeRepository.findByUserIdAndCourseId(token.getUserId(), courseId) != null) {//user
					CourseContent courseContent = courseContentRepository.findByCourseContentId(courseContentId);
					
					if (courseContent == null) {
						throw new AuthException("课程内容不存在");
					}
					if (!courseContent.getCourseId().equals(course.getCourseId())) {
						throw new AuthException("课程内容不属于该课程");
					}
					
					if (courseContent.getState().equals(CourseStateEnum.Close.getCode())) {
						throw new AuthException("课程内容未开放");
					}
					
					dataWrapper.setData(courseContent);
					if (bookRepository.findByUserIdAndCourseContentId(token.getUserId(), courseContent.getCourseContentId()) == null) {
						Book book = new Book();
						book.setBookId(null);
						book.setBookName(courseContent.getBookName());
						book.setBookImgSrc(courseContent.getBookImgSrc());
						book.setReadDate(new Date());
						book.setUserId(token.getUserId());
						book.setIsCourseBook(BookOrigin.CourseBook.getCode());
						book.setCourseContentId(courseContent.getCourseContentId());
						try {
							bookRepository.save(book);
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
					
					//add read
					
				} else {
					throw new AuthException("未激活此课程");
				}
			} else {
				throw new AuthException("用户未登录");
			}
		}
		return dataWrapper;
	}

	@Override
	public DataWrapper<Course> getCourseDetails(Long courseId) {
		// TODO Auto-generated method stub
		Course course = courseRepository.findByCourseId(courseId);
		DataWrapper<Course> dataWrapper = new DataWrapper<Course>();
		dataWrapper.setData(course);
		return dataWrapper;
	}

	@Override
	public DataWrapper<CourseArrangement> getArrangeMentDetails(Long courseArrangementId) {
		// TODO Auto-generated method stub
		CourseArrangement courseArrangement = courseArrangementRepository.findByCourseArrangementId(courseArrangementId);
		DataWrapper<CourseArrangement> dataWrapper = new DataWrapper<CourseArrangement>();
		dataWrapper.setData(courseArrangement);
		return dataWrapper;
	}

	@Override
	public DataWrapper<List<Course>> getUserCourseListByUserId(Long userId, Integer numberPerPage, Integer currentPage) {
		// TODO Auto-generated method stub
		
		return courseRepository.getCourseListByUserId(userId, numberPerPage, currentPage, courseCodeRepository.countByUserId(userId).intValue());
	}

	@Override
	public DataWrapper<Void> changeState(Long courseId, Integer state) {
		// TODO Auto-generated method stub
		
		if (CourseStateEnum.parse(state) == null) {
			throw new ParameterException("课程状态错误");
		}
		Course course = courseRepository.findByCourseId(courseId);
		if (course == null) {
			throw new AuthException("课程不存在");
		}
		try {
			course.setState(state);
			courseRepository.save(course);
		} catch (Exception e) {
			// TODO: handle exception
			throw new DBException("数据库错误",e);
		}
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> changeArrangementState(Long courseArrangementId, Integer state) {
		// TODO Auto-generated method stub
		if (CourseStateEnum.parse(state) == null) {
			throw new ParameterException("课程安排状态错误");
		}
		CourseArrangement courseArrangement = courseArrangementRepository.findByCourseArrangementId(courseArrangementId);
		if (courseArrangement == null) {
			throw new AuthException("课程安排不存在");
		}
		try {
			courseArrangement.setState(state);
			courseArrangementRepository.save(courseArrangement);
		} catch (Exception e) {
			// TODO: handle exception
			throw new DBException("数据库错误",e);
		}
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> changeContentState(Long courseContentId, Integer state) {
		// TODO Auto-generated method stub
		if (CourseStateEnum.parse(state) == null) {
			throw new ParameterException("课程内容状态错误");
		}
		CourseContent courseContent = courseContentRepository.findByCourseContentId(courseContentId);
		if (courseContent == null) {
			throw new AuthException("课程内容不存在");
		}
		try {
			courseContent.setState(state);
			courseContentRepository.save(courseContent);
		} catch (Exception e) {
			throw new DBException("数据库错误",e);
		}
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> copyCourse(Long courseId) {
		// TODO Auto-generated method stub
		Course course = courseRepository.findByCourseId(courseId);
		if (course == null) {
			throw new AuthException("课程不存在");
		}
		
		Course newCourse = CopyUtils.copeCourse(course);
		
		try {
			//copy course
			courseRepository.save(newCourse);
			
			//copy course arrangement
			Page<CourseArrangement> arragenmentPage = courseArrangementRepository.findArrangementListByCourseId(courseId, null);
			
			if (arragenmentPage != null && arragenmentPage.getContent() != null && arragenmentPage.getContent().size() > 0) {
				for(CourseArrangement courseArrangementToCopy :arragenmentPage.getContent()) {
					CourseArrangement newCourseArrangement = CopyUtils.copyCourseArrangement(courseArrangementToCopy, newCourse.getCourseId(), courseArrangementToCopy.getArrangementOrder());
					
					if (newCourseArrangement == null) {
						continue;
					}
					courseArrangementRepository.save(newCourseArrangement);
					Page<CourseContent> contentPage = courseContentRepository.findContentListByArrangementId(courseArrangementToCopy.getCourseArrangementId(), null);
					
					if (contentPage != null && contentPage.getContent() != null && contentPage.getContent().size() > 0) {
						List<CourseContent> newCourseContentList = CopyUtils.copyCourseContentList(contentPage.getContent(), 
								newCourse.getCourseId(), newCourseArrangement.getCourseArrangementId(), null);
						courseContentRepository.save(newCourseContentList);
						
					}
					
				}
				
			}
			
			
			//copy course content
		} catch (Exception e) {
			throw new DBException("数据库错误",e);
		}
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		return dataWrapper;
	}
	
	
	

}
