package com.course.controller;

import com.course.accessctro.RoleContro;
import com.course.enums.RoleEnum;
import com.course.form.LoginForm;
import com.course.form.UserRegisterForm;
import com.course.model.User;
import com.course.service.UserService;
import com.course.utils.ResultVOUtil;
import com.course.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zty200329
 * @date 2020/7/15 9:38
 * @describe:
 */
@RestController
@Slf4j
@RequestMapping("/api/user")
@Api(tags = "测试接口")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * @param
     * @return
     */
    @PostMapping("/insertUser")
    @ApiOperation("注册用户")
    public ResultVO insertUser(UserRegisterForm registerForm){
        return userService.UserRegister(registerForm);
    }

    @PostMapping("/login")
    @ApiOperation("登录")
    public ResultVO login(LoginForm loginForm, HttpServletResponse response){
        return userService.login(loginForm,response);
    }

    @PostMapping("/delete")
    @ApiOperation("测试权限")
    @RoleContro(role = RoleEnum.ADMIN)
    public ResultVO delete(String userName){
        User user = userService.getCurrentUser();
        log.info(user.toString());
        return ResultVOUtil.success();
    }
}
