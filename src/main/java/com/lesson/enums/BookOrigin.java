package com.lesson.enums;

public enum BookOrigin {
	PersonalBook("个人书籍",0),
	CourseBook("课程书籍",1);
	
	private String name;
	private int code;
	
	BookOrigin(String name, int code) {
		this.name = name;
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	

}
