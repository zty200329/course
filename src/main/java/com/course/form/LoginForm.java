package com.course.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author: zty
 * @date 2019/9/6 上午10:40
 * @description: 登录表单
 */
@Data
public class LoginForm {

    @ApiModelProperty("用户名")
    private String cardId;

    @ApiModelProperty("密码")
    private String password;
}
