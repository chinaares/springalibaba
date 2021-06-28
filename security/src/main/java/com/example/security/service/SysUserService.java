package com.example.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.security.entity.SysUser;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wh
 * @since 2021-06-25
 */
public interface SysUserService extends IService<SysUser> {

    SysUser getUserByUsername(String username);

    List<SysUser> getUsers();

    Integer deleteUserById(Integer userId);
}
