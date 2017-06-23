package com.lesson.service;

import com.lesson.utils.DataWrapper;

public interface CodeService {
	DataWrapper<Void> getCode(String phone);

}
