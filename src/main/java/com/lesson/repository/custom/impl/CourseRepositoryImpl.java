package com.lesson.repository.custom.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
	public DataWrapper<List<Course>> getCourseList(Integer isFree, Integer type, Integer numberPerPage,
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

	@Override
	public DataWrapper<List<Course>> getCourseListFromUser(Integer type, Integer numberPerPage, Integer currentPage) {
		// TODO Auto-generated method stub
		return null;
	}

}
