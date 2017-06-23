package com.lesson.enums;



public enum UserType {
	Admin(0),
	User(1),
	;
	private Integer code;
	
	UserType(Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	
	
}
