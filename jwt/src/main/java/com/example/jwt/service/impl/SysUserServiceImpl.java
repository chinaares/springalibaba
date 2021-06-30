package com.example.jwt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.jwt.entity.SysUser;
import com.example.jwt.mapper.SysUserMapper;
import com.example.jwt.service.SysUserService;
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
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getUserByUsername(String username) {
        final QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper<>();
        sysUserQueryWrapper.eq("username",username);
        return sysUserMapper.selectOne(sysUserQueryWrapper);
    }

    @Override
    public List<SysUser> getUsers() {
        return sysUserMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public Integer deleteUserById(Integer userId) {
        return sysUserMapper.deleteById(userId);
    }
}
