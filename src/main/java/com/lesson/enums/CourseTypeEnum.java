package com.lesson.enums;


public enum CourseTypeEnum {
	Sun(1,"太阳课程"),
	Moon(2,"月亮课程"),
	Star(3,"星星课程"),
	NatureRead(4,"自然拼读");
	private int code;
	private String name;
	
	CourseTypeEnum(int code, String name) {
		this.code = code;
		this.name= name();
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
	public static CourseTypeEnum parse(int code) {
        for (CourseTypeEnum theEnum : CourseTypeEnum.values()) {
            if (theEnum.getCode() == code) {
                return theEnum;
            }
        }
        return null;
    }
	

}
