package com.lesson.service.impl;

import org.springframework.stereotype.Service;

import com.lesson.service.QiNiuService;
import com.lesson.utils.DataWrapper;
import com.lesson.utils.QiNiuUtil;

@Service
public class QiNiuServiceImpl implements QiNiuService {

	@Override
	public DataWrapper<String> getQiNiuToken() {
		// TODO Auto-generated method stub
		DataWrapper<String> dataWrapper = new DataWrapper<String>();
		dataWrapper.setData(QiNiuUtil.getUploadToken());
		return dataWrapper;
	}

}
