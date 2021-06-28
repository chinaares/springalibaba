package com.example.security.controller;

import com.example.common.exception.basic.APIResponse;
import com.example.security.entity.SysUser;
import com.example.security.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户表控制器
 */
@RestController
@RequestMapping("/user")
public class UserController {

    //属性注入
    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取用户列表
     * @return
     */
    @GetMapping("/getUsers")
    public APIResponse getUsers() {
        List<SysUser> users = this.sysUserService.getUsers();
        return APIResponse.ok(users);
    }

    /**
     * 根据用户ID删除用户
     * @return
     */
    @DeleteMapping("/deleteUserById")
    public APIResponse deleteUserById(@RequestParam(required = true) Integer userId){
        Integer result = this.sysUserService.deleteUserById(userId);
        return APIResponse.ok(result);
    }
}