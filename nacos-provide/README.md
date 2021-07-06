**跨域配置方法一**
```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路径
        registry.addMapping("/**")
                //设置允许跨域请求的域名
                //当**Credentials为true时，**Origin不能为星号，需为具体的ip地址【如果接口不带cookie,ip无需设成具体ip】
                .allowedOrigins("http://localhost:9527", "http://127.0.0.1:9527", "http://127.0.0.1:8082", "http://127.0.0.1:8083")
                //是否允许证书 不再默认开启
                .allowCredentials(true)
                //设置允许的方法
                .allowedMethods("*")
                //跨域允许时间
                .maxAge(3600);
    }
}
```
**跨域配置方法二**
```java
@Configuration
public class CorsConfig {
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setMaxAge(3600L);
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
```
**跨域配置方法三**
```java
@Controller
@CrossOrigin  //使用注解（@CrossOrigin）
public class SysLogController {

}
```
**跨域配置方法四**
```java
//手工设置响应头（HttpServletResponse ）
//这种方式，可以自己手工加到，具体的controller，inteceptor，filter等逻辑里。
public class SysLogController {
    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        return "success";
    }
}

//总结：以上是设置cors跨域后端解决的四种方式，本质都是类似最后一种设置响应头，不过都是各自包实现了不同的封装逻辑。
```