package com.example.security.config;

import com.example.common.exception.basic.ResponseCode;
import com.example.common.utils.Md5Util;
import com.example.security.entity.SysPermission;
import com.example.security.entity.SysUser;
import com.example.security.service.SysPermissionService;
import com.example.security.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义认证提供者，对用户名和密码进行验证
 *
 * @author Administrator
 */
@Service
public class CustomizeAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysPermissionService permissionService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //获取前端传过来的的username和password
        String username = authentication.getName();
        String prepassword = (String) authentication.getCredentials();
        //定义UserDetails对象，构造再返回
        UserDetails userDetails = null;
        SysUser sysUser = this.userService.getUserByUsername(username);
        if (sysUser == null) {
            throw new InternalAuthenticationServiceException("用户不存在");
        }
        //根据用户名获取权限列表
        List<SysPermission> permissions = permissionService.getPermissionsByUserId(sysUser.getId());
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (permissions != null && permissions.size() > 0) {
            permissions.stream().forEach(item -> {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(item.getKey());
                authorities.add(grantedAuthority);
            });
        }
        //构造UserDetails对象
        userDetails = new User(username, sysUser.getPassword(), authorities);
        if (authentication.getCredentials() == null) {
            throw new BadCredentialsException(ResponseCode.LOGIN_INVALID.getMsg());
            //获取前端传过来的密码，经过加密之后和数据库保存的密码比较是否一致
        } else if (!Md5Util.encode(prepassword).equals(sysUser.getPassword())) {
            throw new BadCredentialsException(ResponseCode.LOGIN_INVALID.getMsg());
        }
        //返回UsernamePasswordAuthenticationToken对象
        return new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(), userDetails.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}