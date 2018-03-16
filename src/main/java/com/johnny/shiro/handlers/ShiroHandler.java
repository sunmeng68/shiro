package com.johnny.shiro.handlers;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("shiro")
public class ShiroHandler
{

    @RequestMapping("login")
    public String shiroLogin(@RequestParam("username") String username,
            @RequestParam("password") String password)
    {

        Subject currentUser = SecurityUtils.getSubject();
        // 判断用户是否 登陆
        if (!currentUser.isAuthenticated())
        {
            UsernamePasswordToken token = new UsernamePasswordToken(username,
                    password);
            token.setRememberMe(true);

            try
            {

                currentUser.login(token);

            }
            catch (AuthenticationException e)
            {
                System.out.println("登陆失败:" + e.getMessage());
            }

        }

        return "redirect:/jsp/list.jsp";

    }

}
