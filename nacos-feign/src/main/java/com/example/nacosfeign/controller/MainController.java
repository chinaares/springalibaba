package com.example.nacosfeign.controller;

import com.example.nacosfeign.remote.AuthFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequestMapping("api/feign")
@RestController
@Api(tags = "nacos-feign")
public class MainController {

    @Autowired
    private AuthFeignClient authFeignClient;

    @GetMapping("/hello")
    @ApiOperation(value = "你好", notes = "hello")
    public String hello() {
        return authFeignClient.helloNacos();
    }

    /**
     * 跳转到首页.
     * @return
     */
    @GetMapping("/user/index")
    public String toIndex(HttpServletRequest request) {
        //getSession()：返回与此请求关联的当前会话，或者如果该请求没有会话，则创建一个会话。
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("isLogin", true);
        //设置会话超时时间(秒)。表示用户此时间内没有访问本系统时，则会话失效。默认为 30 分钟
        //只要用户访问本应用下的任意资源都是可以的，都能维持会话. 当用户关闭浏览器后会话也会结束。下一次会是一次新的会话.
        httpSession.setMaxInactiveInterval(60 * 60);
        //返回到 thymeleaf 模板目录下的 index.html 页面
        return "index";
    }

}
