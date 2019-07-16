package com.example.demo.Enums;

public enum ExceptionEnums {
    UNKNOW_ERRPR(-1,"未知错误"),
    SUCCESS(0,"成功"),
    PRIMAKET_SCHOOL(100,"未达到指年龄"),
    MIDDLE_SCHOOL(101,"收入不足"),
    UNFIND_ERROR(110,"未发现记录"),
    PASS_ERROR(2,"用户名或密码错误"),
    USER_TEL_ERROR(3,"手机号错误")

    ;

    private  Integer code;
    private  String msg;
    ExceptionEnums(Integer code,String msg) {
        this.code=code;
        this.msg=msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
