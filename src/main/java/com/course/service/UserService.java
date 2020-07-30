package com.course.service;

import com.course.form.LoginForm;
import com.course.form.UserRegisterForm;
import com.course.model.User;
import com.course.vo.ResultVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zty200329
 * @date 2020/7/15 10:51
 * @describe:
 */
@Service
public interface UserService {
    /**
     * 通过账号获取用户
     *
     * @param cardId
     * @return
     */
    User getUserByUsername(String cardId);

    /**
     * 获取当前用户
     *
     * @return
     */
    User getCurrentUser();

    /**
     * 用户注册
     * @param userRegisterForm
     * @return
     */
    ResultVO UserRegister(UserRegisterForm userRegisterForm);

    /**
     * 登录
     * @param loginForm
     * @param response
     * @return
     */
    ResultVO login(LoginForm loginForm, HttpServletResponse response);
}
