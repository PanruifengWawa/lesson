package com.lesson.enums;

import java.io.Serializable;

/**
 * Created by TIANCHENGYUAN103 on 2015-12-04.
 */
public enum ErrorCodeEnum implements Serializable {

	成功("成功", 0),
	
	权限不足("权限不足",1),
	数据库错误("数据库错误",2),
	参数错误("参数错误",3),
	验证码错误("验证码错误",4),
	未知错误("未知错误",20);

    private String label;
    private Integer code;

    ErrorCodeEnum() {
    	
    }

    ErrorCodeEnum(String label, Integer code) {
        this.label = label;
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code.toString();
    }

    public static ErrorCodeEnum parse(int code) {
        for (ErrorCodeEnum theEnum : ErrorCodeEnum.values()) {
            if (theEnum.getCode() == code) {
                return theEnum;
            }
        }
        return 成功;
    }
}
