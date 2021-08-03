package com.example.nacosgateway.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.nacosgateway.entity.SysUser;
import reactor.core.publisher.Mono;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wh
 * @since 2021-06-25
 */
public interface SysUserService extends IService<SysUser> {

    Mono<SysUser> findByUsername(String username);

    Mono<SysUser> updateUser(SysUser updatedUser);
}
