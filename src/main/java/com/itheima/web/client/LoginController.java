package com.itheima.web.client;

import com.itheima.dao.UserAuthorityMapper;
import com.itheima.dao.UserMapper;
import com.itheima.model.domain.User;
import com.itheima.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
/**
 * @Classname LoginController
 * @Description 用户登录模块
 * @Date 2019-3-14 14:15
 * @Created by CrazyStone
 */
@Controller
public class LoginController {
    @Autowired
    private IUserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserAuthorityMapper userAuthorityMapper;
    // 向登录页面跳转，同时封装原始页面地址
    @GetMapping(value = "/login")
    public String login(HttpServletRequest request, Map map) {
        // 分别获取请求头和参数url中的原始访问路径
        String referer = request.getHeader("Referer");
        String url = request.getParameter("url");
        System.out.println("referer= "+referer);
        System.out.println("url= "+url);

        // 如果参数url中已经封装了原始页面路径，直接返回该路径
        if (url!=null && !url.equals("")){
            map.put("url",url);
            // 如果请求头本身包含登录，将重定向url设为空，让后台通过用户角色进行选择跳转
        }else if (referer!=null && referer.contains("/login")){
            map.put("url", "");
        }else {
            // 否则的话，就记住请求头中的原始访问路径
            map.put("url", referer);
        }
        return "comm/login";
    }

    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request,Map map) {
        // 分别获取请求头和参数url中的原始访问路径
        String referer = request.getHeader("Referer");
        String url = request.getParameter("url");
        System.out.println("referer= "+referer);
        System.out.println("url= "+url);
        return "comm/logout";
    }

    // 对Security拦截的无权限访问异常处理路径映射
    @GetMapping(value = "/errorPage/{page}/{code}")
    public String AccessExecptionHandler(@PathVariable("page") String page, @PathVariable("code") String code) {
        return page+"/"+code;
    }

    @GetMapping(value = "/register")
    public String register() {
        return "comm/register";
    }
    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               @RequestParam("email") String email,
                               RedirectAttributes redirectAttributes) {
        // 注册用户
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        int count = userService.registerUser(user);
        if (count > 0) {
            redirectAttributes.addFlashAttribute("success", "注册成功，请登录！");
            user= userMapper.getUserByUsername(username);
            userAuthorityMapper.setUserAuthority(user.getId(),2);
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("error", "注册失败，请重新注册！");
            return "redirect:/register";
        }
    }
}

