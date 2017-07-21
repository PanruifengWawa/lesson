package com.lesson.repository.custom.impl;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.lesson.enums.CourseFreeEnum;
import com.lesson.enums.CourseStateEnum;
import com.lesson.models.Course;
import com.lesson.repository.custom.CourseRepositoryCustom;
import com.lesson.utils.DaoUtils;
import com.lesson.utils.DataWrapper;

@Transactional

public class CourseRepositoryImpl implements CourseRepositoryCustom  {
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<Course>> getCourseList(Integer state, Integer isFree, Integer type, Integer numberPerPage,
			Integer currentPage) {
		// TODO Auto-generated method stub

		if (numberPerPage == null) {
			numberPerPage = -1;
		}
		if (currentPage == null) {
			currentPage = -1;
		}
		List<Course> ret = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Course.class);
		
		if (isFree != null) {
			criteria.add(Restrictions.eq("isFree", isFree));
		}
		if (type != null) {
			criteria.add(Restrictions.eq("type", type));
		}
		if (state != null) {
			criteria.add(Restrictions.eq("state", state));
		}
		
		criteria.setProjection(Projections.rowCount());
		int totalItemNum = ((Long) criteria.uniqueResult()).intValue();
		int totalPageNum = DaoUtils.getTotalPageNum(totalItemNum, numberPerPage);
		
		
		criteria.setProjection(null);
		criteria.addOrder(Order.desc("createDate"));
		if (currentPage > 0 && numberPerPage > 0) {
			criteria.setMaxResults(numberPerPage);
			criteria.setFirstResult((currentPage-1)*numberPerPage);
		}
		
		
		try {
			ret = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DataWrapper<List<Course>> dataWrapper = new DataWrapper<List<Course>>(); 
		dataWrapper.setData(ret);
        dataWrapper.setTotalNumber(totalItemNum);
        dataWrapper.setCurrentPage(currentPage);
        dataWrapper.setTotalPage(totalPageNum);
        dataWrapper.setNumberPerPage(numberPerPage);
		return dataWrapper;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<Course>> getCourseListFromUser(Long userId, Integer type, Integer numberPerPage, Integer currentPage) {
		// TODO Auto-generated method stub
		DataWrapper<List<Course>> dataWrapper = new DataWrapper<List<Course>>();
		List<Course> ret = null;
		Session session = sessionFactory.getCurrentSession();
        String sql = "select course.course_id as courseId, course.name as name, course.img_src as imgSrc, course.is_free as isFree, course.type as type, course.link as link, course.create_date as createDate,  course.state as state, " +
        			 "(case when course_code.course_code_id is null then 0 else 1 end) as isBought from " + 
        					"(select * from t_course where type = ? and is_free = ? and state = ?) course " + 
        							"left join " +
        					"(select * from t_course_code where user_id = ?) course_code " +
        					"on course.course_id = course_code.course_id order by isBought desc,isFree desc, create_date desc";
        Query query=session.createSQLQuery(sql)
				.addScalar("courseId",StandardBasicTypes.LONG)
				.addScalar("name",StandardBasicTypes.STRING)
        		.addScalar("imgSrc",StandardBasicTypes.STRING)
        		.addScalar("isFree",StandardBasicTypes.INTEGER)
        		.addScalar("state",StandardBasicTypes.INTEGER)
        		.addScalar("type",StandardBasicTypes.INTEGER)
        		.addScalar("createDate",StandardBasicTypes.TIMESTAMP)
        		.addScalar("link",StandardBasicTypes.STRING)
        		.addScalar("isBought", StandardBasicTypes.INTEGER)
        		.setResultTransformer(Transformers.aliasToBean(Course.class));
        
        if (numberPerPage == null) {
        	numberPerPage = -1;
		}
        if (currentPage == null) {
        	currentPage = -1;
		}
        int totalltemNum = getCouserCountByType(type).intValue();
        int totalPageNum = DaoUtils.getTotalPageNum(totalltemNum, numberPerPage);
       
        
        if (numberPerPage > 0 && currentPage > 0) {
            query.setMaxResults(numberPerPage);
            query.setFirstResult((currentPage - 1) * numberPerPage);
        }
        
        try {
        	query.setParameter(0, type);
        	query.setParameter(1, CourseFreeEnum.No.getCode());
        	query.setParameter(2, CourseStateEnum.Open.getCode());
        	query.setParameter(3, userId);
        	ret=query.list();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        dataWrapper.setCurrentPage(currentPage);
        dataWrapper.setNumberPerPage(numberPerPage);
        dataWrapper.setTotalPage(totalPageNum);
        dataWrapper.setTotalNumber(totalltemNum);
        
        dataWrapper.setData(ret);
        return dataWrapper;
	}


	@SuppressWarnings("unchecked")
	public BigInteger getCouserCountByType(Integer type) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from  t_course where type = ? and is_free = ? and state = ?";
		List<BigInteger> ret = null;
		Session session = sessionFactory.getCurrentSession();
        try {
            Query query = session.createSQLQuery(sql);
            query.setParameter(0, type);
            query.setParameter(1, CourseFreeEnum.No.getCode());
            query.setParameter(2, CourseStateEnum.Open.getCode());
            ret = query.list();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
			return ret.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<Course>> getCourseListByUserId(Long userId, Integer numberPerPage, Integer currentPage, Integer totalltemNum) {
		// TODO Auto-generated method stub
		DataWrapper<List<Course>> dataWrapper = new DataWrapper<List<Course>>();
		List<Course> ret = null;
		Session session = sessionFactory.getCurrentSession();
        String sql = "select course.* from t_course course where course.course_id in (select course_id from t_course_code where user_id = ?)";
        Query query=session.createSQLQuery(sql)
				.addEntity(Course.class);
        
        if (numberPerPage == null || numberPerPage <= 0 || numberPerPage > 10) {
			numberPerPage = 10;
		}
		if (currentPage == null || currentPage <= 0) {
			currentPage = 1;
		}
        int totalPageNum = DaoUtils.getTotalPageNum(totalltemNum, numberPerPage);
       
        
        if (numberPerPage > 0 && currentPage > 0) {
            query.setMaxResults(numberPerPage);
            query.setFirstResult((currentPage - 1) * numberPerPage);
        }
        
        try {
        	query.setParameter(0, userId);
        	ret=query.list();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        dataWrapper.setCurrentPage(currentPage);
        dataWrapper.setNumberPerPage(numberPerPage);
        dataWrapper.setTotalPage(totalPageNum);
        dataWrapper.setTotalNumber(totalltemNum);
        
        dataWrapper.setData(ret);
        return dataWrapper;
	}
}
