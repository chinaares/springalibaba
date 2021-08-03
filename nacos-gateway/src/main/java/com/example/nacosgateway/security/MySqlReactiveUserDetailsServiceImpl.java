package com.example.nacosgateway.security;

import com.example.nacosgateway.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsPasswordService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 身份认证类
 *
 */
@Slf4j
@Component
public class MySqlReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService, ReactiveUserDetailsPasswordService {

    private static final String USER_NOT_EXISTS = "用户不存在！";

    @Autowired
    private SysUserService sysUserService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return sysUserService.findByUsername(username)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new UsernameNotFoundException(USER_NOT_EXISTS))))
                .doOnNext(u -> log.info(String.format("查询账号成功  user:%s password:%s", u.getUsername(), u.getPassword())))
                .cast(UserDetails.class);
    }

    @Override
    public Mono<UserDetails> updatePassword(UserDetails user, String newPassword) {
        return sysUserService.findByUsername(user.getUsername())
                .switchIfEmpty(Mono.defer(() -> Mono.error(new UsernameNotFoundException(USER_NOT_EXISTS))))
                .map(foundedUser -> {
                    foundedUser.setPassword(passwordEncoder.encode(newPassword));
                    return foundedUser;
                })
                .flatMap(sysUserService::updateUser)
                .cast(UserDetails.class);
    }
}