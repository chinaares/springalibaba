package com.example.jwt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.jwt.entity.SysPermission;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wh
 * @since 2021-06-25
 */
public interface SysPermissionService extends IService<SysPermission> {

    List<SysPermission> getPermissionsByUserId(Integer userId);

    List<SysPermission> selectListByPath(String requestUrl);
}
