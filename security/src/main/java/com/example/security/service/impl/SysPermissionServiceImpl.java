package com.example.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.security.mapper.SysPermissionMapper;

import com.example.security.entity.SysPermission;
import com.example.security.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wh
 * @since 2021-06-25
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public List<SysPermission> getPermissionsByUserId(Integer userId) {
        return sysPermissionMapper.getPermissionsByUserId(userId);
    }

    @Override
    public List<SysPermission> selectListByPath(String requestUrl) {
        return sysPermissionMapper.findByUrl(requestUrl);
    }
}
