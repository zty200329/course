package com.course.service.impl;

import com.course.dao.UserMapper;
import com.course.enums.ResultEnum;
import com.course.form.LoginForm;
import com.course.form.UserRegisterForm;
import com.course.model.User;
import com.course.security.JwtProperties;
import com.course.security.JwtUserDetailServiceImpl;
import com.course.service.UserService;
import com.course.utils.JwtTokenUtil;
import com.course.utils.ResultVOUtil;
import com.course.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zty200329
 * @date 2020/7/15 10:56
 * @describe:
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtUserDetailServiceImpl jwtUserDetailService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtProperties jwtProperties;
    @Override
    public User getUserByUsername(String cardId) {
        return userMapper.selectUserByUsername(cardId);
    }

    /**
     * 不变
     * @return
     */
    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String cardId = authentication.getName();
        String key = "anonymousUser";
        if (!cardId.equals(key)) {
            return getUserByUsername(cardId);
        }
        return null;
    }

    @Override
    public ResultVO UserRegister(UserRegisterForm userRegisterForm) {
        //如果有验证码，验证码出错下面不执行
        boolean isHave = (userMapper.selectUserByUsername(userRegisterForm.getCardId()) != null);
        if (isHave){
            return ResultVOUtil.error(ResultEnum.USER_HAS_EXIST);
        }
        User user = new User();
        BeanUtils.copyProperties(userRegisterForm,user);
        user.setPassword(passwordEncoder.encode(jwtProperties.getDefaultPassword()));
        log.info("用户信息" + user);
        int result = userMapper.insert(user);
        if (result != 1) {
            return ResultVOUtil.error(ResultEnum.SQL_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO login(LoginForm loginForm, HttpServletResponse response) {
        User user = userMapper.selectUserByUsername(loginForm.getCardId());
        if (user == null) {
            return ResultVOUtil.error(ResultEnum.USER_NOT_EXIST);
        }
        UserDetails userDetails = jwtUserDetailService.loadUserByUsername(loginForm.getCardId());
        if (!(new BCryptPasswordEncoder().matches(loginForm.getPassword(), userDetails.getPassword()))) {
            return ResultVOUtil.error(ResultEnum.PASSWORD_ERROR);
        }
        Authentication token = new UsernamePasswordAuthenticationToken(loginForm.getCardId(), loginForm.getPassword(), userDetails.getAuthorities());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String realToken = jwtTokenUtil.generateToken(userDetails);
        response.addHeader(jwtProperties.getTokenName(), realToken);
        Map<String, Serializable> map = new HashMap<>();
        map.put("name",user.getName());
        map.put("role", user.getRole());
        map.put("token", realToken);
        return ResultVOUtil.success(map);
    }


}
