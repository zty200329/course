package com.course.enums;

import lombok.Getter;

/**
 * @author hobo
 * @description
 */
@Getter
public enum ResultEnum {
    /**
     *
     */

    /**
     * zty定义 1000-1100
     */
    USER_NOT_LOGIN(1000,"用户登录信息过期"),
    USER_NOT_EXIST(1001,"用户不存在"),
    PASSWORD_ERROR(1002, "密码错误"),
    USER_HAS_EXIST(1003, "用户已经存在"),
    SQL_ERROR(1004, "添加用户失败"),

    /**
     * ml定义 1100-1200
     */

    /**
     * lyh定义 1200-1300
     */
    AUTHENTICATION_ERROR(401, "用户认证失败,请重新登录"),
    PERMISSION_DENNY(403, "权限不足"),
    NOT_FOUND(404, "url错误,请求路径未找到"),
    SERVER_ERROR(500, "服务器未知错误:%s"),
    BIND_ERROR(511, "参数校验错误:%s"),
    REQUEST_METHOD_ERROR(550, "不支持%s的请求方式");


    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
