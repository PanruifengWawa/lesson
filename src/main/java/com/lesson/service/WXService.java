package com.lesson.service;

import java.util.Map;

import com.lesson.utils.DataWrapper;

public interface WXService {
	DataWrapper<Map<String, String>> getJSConfig(String url);

}
