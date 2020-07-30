package com.course.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zty200329
 * @date 2020/7/15 9:43
 * @describe:
 */
@Data
public class RegisterForm {

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("用户权限")
    private Integer role;
}
