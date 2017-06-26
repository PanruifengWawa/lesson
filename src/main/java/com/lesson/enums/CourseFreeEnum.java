package com.lesson.enums;


public enum CourseFreeEnum {
	No(0,"公开课"),
	Yes(1,"收费课")
	;
	private int code;
	private String name;
	
	CourseFreeEnum(int code,String name) {
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
	
	public static CourseFreeEnum parse(int code) {
        for (CourseFreeEnum theEnum : CourseFreeEnum.values()) {
            if (theEnum.getCode() == code) {
                return theEnum;
            }
        }
        return null;
    }
}
