package com.mohe.blog.web.admin;

import com.mohe.blog.po.User;
import com.mohe.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * login controller
 *
 * @author mo
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 对/admin路径进行初始化，默认页面
     *
     * @return
     */
    @GetMapping
    public String loginPage() {
        return "admin/login";
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session, RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);
        if (user != null) {
            //防止密码被拿到
            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/index";
        } else {
            //验证不通过，即重定向到登陆页面
            attributes.addFlashAttribute("message", "用户名和密码错误");
            return "redirect:/admin";
        }
    }

    /**
     * 用户注销登录
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        //注销登录信息，即将session中的用户信息清空掉
        session.removeAttribute("user");
        //重定向到登录页面
        return "redirect:/admin";
    }
}
