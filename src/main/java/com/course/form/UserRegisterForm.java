package com.course.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author: zty
 * @date 2019/10/12 下午7:52
 * @description:
 */
@Data
public class UserRegisterForm {
    @ApiModelProperty("工号")
    private String cardId;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("权限")
    private Integer role;
}
