package com.lesson.enums;

public enum CourseCodeStateNums {
	No(0,"未使用"),
	Yes(1,"已使用")
	;
	
	private int code;
	private String name;
	
	CourseCodeStateNums(int code,String name) {
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
	
	public static CourseCodeStateNums parse(int code) {
        for (CourseCodeStateNums theEnum : CourseCodeStateNums.values()) {
            if (theEnum.getCode() == code) {
                return theEnum;
            }
        }
        return null;
    }
}
