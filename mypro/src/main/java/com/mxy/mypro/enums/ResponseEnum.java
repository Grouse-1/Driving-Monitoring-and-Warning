package com.mxy.mypro.enums;

public enum ResponseEnum {
    SUCCESS(200, "成功"),

    ERROR(500, "失败");

    Integer code;

    String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
