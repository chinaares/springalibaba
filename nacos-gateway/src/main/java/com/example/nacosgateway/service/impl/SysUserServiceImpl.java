package com.example.nacosgateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.nacosgateway.entity.SysUser;
import com.example.nacosgateway.mapper.SysUserMapper;
import com.example.nacosgateway.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

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
    public Mono<SysUser> findByUsername(String username) {
        SysUser user=sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("username",username));
        return user == null ? Mono.empty() : Mono.just(user);

    }

    @Transactional
    @Override
    public Mono<SysUser> updateUser(SysUser entity) {
         sysUserMapper.updateById(entity);
        SysUser user=sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("id",entity.getId()));
        return user == null ? Mono.empty() : Mono.just(user);
    }
}
