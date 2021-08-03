### springCloud网关gateway
1. 设置过滤规则
2. 提供(knife4j,swagger2)API文档对外的统一入口服务

#### spring security设置要采用响应式配置，基于WebFlux中WebFilter实现，与Spring MVC的Security是通过Servlet的Filter实现类似，也是一系列filter组成的过滤链。
|Reactive|Web|
|:---:|:---:|
|@EnableWebFluxSecurity|@EnableWebSecurity|
|ReactiveSecurityContextHolder|SecurityContextHolder|
|AuthenticationWebFilter|FilterSecurityInterceptor|
|ReactiveAuthenticationManager|AuthenticationManager|
|ReactiveUserDetailsService|UserDetailsService|
|ReactiveAuthorizationManager|AccessDecisionManager|