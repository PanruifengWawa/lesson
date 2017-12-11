package com.lesson.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lesson.service.WXService;
import com.lesson.utils.DataWrapper;
import com.lesson.utils.WXUtil;

@Service
public class WXServiceImpl implements WXService {

	@Override
	public DataWrapper<Map<String, String>> getJSConfig(String url) {
		// TODO Auto-generated method stub
		DataWrapper<Map<String, String>> dataWrapper = new DataWrapper<Map<String, String>>();
		dataWrapper.setData(WXUtil.getJSConfig(url));
		return dataWrapper;
	}

}
