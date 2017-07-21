package com.lesson.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lesson.exceptions.AuthException;
import com.lesson.exceptions.DBException;
import com.lesson.models.Link;
import com.lesson.repository.LinkRepository;
import com.lesson.service.LinkService;
import com.lesson.utils.DataWrapper;

@Service
public class LinkServiceImpl implements LinkService {

	@Autowired
	LinkRepository linkRepository;
	
	@Override
	public DataWrapper<Void> updateLink(Link link) {
		// TODO Auto-generated method stub
		Link linkInDB = linkRepository.findByLinkId(link.getLinkId());
		if (linkInDB == null) {
			throw new AuthException("链接不存在");
		}
		
		if (link.getLinkSrc() != null && !link.getLinkSrc().equals("")) {
			linkInDB.setLinkSrc(link.getLinkSrc());
		}
		try {
			linkRepository.save(link);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new DBException("数据库错误",e);
		}
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		return dataWrapper;
	}

	@Override
	public DataWrapper<Link> getLinkById(Long linkId) {
		// TODO Auto-generated method stub
		DataWrapper<Link> dataWrapper = new DataWrapper<Link>();
		dataWrapper.setData(linkRepository.findByLinkId(linkId));
		return dataWrapper;
	}

}
