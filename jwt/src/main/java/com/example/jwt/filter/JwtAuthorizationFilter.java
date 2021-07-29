package com.example.jwt.filter;

import com.example.jwt.utils.JwtTokenUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 验证token(jwt验证是添加)
 */
@Component
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    @Autowired
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * 鉴权
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain){
        String tokenHead=request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        if (tokenHead==null||!tokenHead.startsWith(JwtTokenUtil.TOKEN_PREFIX)){
            chain.doFilter(request,response);
            return;
        }
        // 如果请求头中有token，则进行解析，并且设置认证信息
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHead));
        super.doFilterInternal(request, response, chain);
    }

    /**
     * 这里从token中获取用户信息并新建一个token
     * @param tokenHead
     * @return new token
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHead){
        String token=tokenHead.replace(JwtTokenUtil.TOKEN_PREFIX,"");
        if(JwtTokenUtil.isExpiration(token)){
            return null;
        }
        String username=JwtTokenUtil.getUsername(token);
        String role=JwtTokenUtil.getRole(token);
        if (username!=null){
            List<SimpleGrantedAuthority> list=new ArrayList<>();
            Arrays.stream(role.split(",")).forEach(item->{
                list.add(new SimpleGrantedAuthority(item));
            });
            return new UsernamePasswordAuthenticationToken(username,null, list);
        }
        return null;
    }
}