package com.course.security;


import com.course.model.User;
import com.course.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @ClassName JwtUserDetailServiceImpl
 * @Author zty
 * @Date 19-4-22 下午7:24
 * @Description UserDetailService实现类
 **/

@Service
@Slf4j
public class JwtUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            log.info("此用户不存在");
            throw new UsernameNotFoundException(String.format("用户名为 %s 的用户不存在", username));
        } else {
            Integer role = user.getRole();
            return new JwtUser(username, user.getPassword(), role);
        }
    }
}
