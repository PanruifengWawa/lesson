package com.lesson.enums;

public enum CourseStateEnum {
	Close(0,"未开课"),
	Open(1,"已开课")
	;
	private int code;
	private String name;
	
	CourseStateEnum(int code,String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static CourseStateEnum parse(int code) {
        for (CourseStateEnum theEnum : CourseStateEnum.values()) {
            if (theEnum.getCode() == code) {
                return theEnum;
            }
        }
        return null;
    }

}
