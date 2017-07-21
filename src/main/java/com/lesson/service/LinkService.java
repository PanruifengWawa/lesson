package com.lesson.service;

import com.lesson.models.Link;
import com.lesson.utils.DataWrapper;

public interface LinkService {
	DataWrapper<Void> updateLink(Link link);
	DataWrapper<Link> getLinkById(Long linkId);

}
