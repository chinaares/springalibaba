package com.example.security.utils;

//import cn.hutool.db.sql.SqlBuilder;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.apache.commons.collections4.ListUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.util.Collection;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;

/**
 * Token工具类
 */
//@Component
//public class JwtTokenUtil {
//
//    public static final String TOKEN_HEADER="Authorization";
//    public static final String TOKEN_PREFIX="Bearer ";
//    public static final String SECRET="jwtsecret";
//    public static final String ISS="MANAGER_IGOR";
//    private static final long EXPIRATION = 60*60*1000;
//
//    /**
//     * 创建Token
//     * @return token
//     */
//    public static String createToken(UserDetails userDetails){
//        Map<String,Object> claims=new HashMap<>();
//        Collection<? extends GrantedAuthority> authorities=userDetails.getAuthorities();
//        String role=StringUtils.join(authorities, ",");
//        claims.put("role",role);
//        return doGenerateToken(claims,userDetails.getUsername());
//    }
//
//    /**
//     * 从数据声明生成令牌
//     *
//     * @param claims 数据声明
//     * @return 令牌
//     */
//    public static String doGenerateToken(Map<String,Object> claims,String username){
//        Date expiration=new Date(System.currentTimeMillis()+EXPIRATION);
//        return Jwts.builder()
//                //签发时间
//                .setIssuedAt(new Date())
//                //签发者
//                .setIssuer(ISS)
//                //声明
//                .setClaims(claims)
//                //过期时间
//                .setExpiration(expiration)
//                //面向用户
//                .setSubject(username)
//                .signWith(SignatureAlgorithm.HS256,SECRET)
//                .compact();
//    }
//
//    /**
//     * 获取用户名
//     * @param token
//     * @return  username
//     */
//    public static String getUsername(String token){
//        return getTokenBody(token).getSubject();
//    }
//
//    /**
//     * 获取用户角色
//     * @param token
//     * @return  role
//     */
//    public static String getRole(String token){
//        return getTokenBody(token).get("role").toString();
//    }
//
//    /**
//     * 解析token
//     * @param token
//     * @return
//     */
//    public static Claims getTokenBody(String token){
//        return Jwts.parser()
//                .setSigningKey(SECRET)
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    /**
//     * token是否过期
//     * @param token
//     * @return true or false
//     */
//    public static boolean isExpiration(String token){
//        return getTokenBody(token).getExpiration().before(new Date());
//    }
//
//    /**
//     * 刷新token
//     * @param token
//     * @return token
//     */
//    public static String refreshToken(String token){
//        String refreshToken;
//        try {
//            Claims claims=getTokenBody(token);
//            claims.put("create",new Date());
//            refreshToken=doGenerateToken(claims,claims.getSubject());
//        }catch (Exception e){
//            refreshToken=null;
//        }
//        return refreshToken;
//    }
//
//    /**
//     * 验证Token
//     * @return true or false
//     */
//    public static boolean validateToken(String token,String username){
//        String subject=getUsername(token);
//        return (subject.equals(username)
//                &&!isExpiration(token));
//    }
//
//
//}