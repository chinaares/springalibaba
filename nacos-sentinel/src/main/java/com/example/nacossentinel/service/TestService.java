package com.example.nacossentinel.service;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TestService {

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    public String consumer() {
        initSentinelRule();
        Entry entry = null;
        try {
            // 定义一个sentinel保护的资源，名称为test-sentinel-api
            entry = SphU.entry("test-sentinel-api");
            final String forObject = restTemplate.getForObject("http://nocas-provide/api/helloNacos", String.class);
            // 模拟执行被保护的业务逻辑耗时
            Thread.sleep(100);
            return forObject;
        } catch (BlockException e) {
            // 如果被保护的资源被限流或者降级了，就会抛出BlockException
            log.warn("资源被限流或降级了", e);
            return "资源被限流或降级了";
        } catch (InterruptedException e) {
            return "发生InterruptedException";
        } finally {
            if (entry != null) {
                entry.exit();
            }

            ContextUtil.exit();
        }
    }

    // 限流与阻塞处理
    @SentinelResource(value = "doSomeThing", blockHandler = "exceptionHandler")
    public void doSomeThing(String str) {
        initFlowRules();
        log.info(str);
    }

    public void exceptionHandler(String str, BlockException ex) {
        log.error("blockHandler：" + str, ex);
    }

    // 熔断与降级处理
    @SentinelResource(value = "doSomeThing2", fallback = "fallbackHandler")
    public void doSomeThing2(String str) {
        log.info(str);
        throw new RuntimeException("发生异常");
    }

    public void fallbackHandler(String str) {
        log.error("fallbackHandler：" + str);
    }


    /**
     * FlowRuleManager.loadRules(List<FlowRule> rules); // 修改流控规则
     * DegradeRuleManager.loadRules(List<DegradeRule> rules); // 修改降级规则
     * SystemRuleManager.loadRules(List<SystemRule> rules); // 修改系统规则
     * AuthorityRuleManager.loadRules(List<AuthorityRule> rules); // 修改授权规则
     *
     * 平均响应时间 (DEGRADE_GRADE_RT)：
     * 当资源的平均响应时间超过阈值（DegradeRule 中的 count，以 ms 为单位）之后，资源进入准降级状态。如果接下来 1s 内持续进入 5 个请求（即 QPS >= 5），它们的 RT 都持续超过这个阈值，那么在接下的时间窗口（DegradeRule 中的 timeWindow，以 s 为单位）之内，对这个方法的调用都会自动地熔断（抛出 DegradeException）。
     * 注意 Sentinel 默认统计的 RT 上限是 4900 ms，超出此阈值的都会算作 4900 ms，若需要变更此上限可以通过启动配置项 -Dcsp.sentinel.statistic.max.rt=xxx 来配置。
     * 异常比例 (DEGRADE_GRADE_EXCEPTION_RATIO)：
     * 当资源的每秒异常总数占通过量的比值超过阈值（DegradeRule 中的 count）之后，资源进入降级状态，即在接下的时间窗口（DegradeRule 中的 timeWindow，以 s 为单位）之内，对这个方法的调用都会自动地返回。
     * 异常比率的阈值范围是 [0.0, 1.0]，代表 0% - 100%。
     * 异常数 (DEGRADE_GRADE_EXCEPTION_COUNT)：
     * 当资源近 1 分钟的异常数目超过阈值之后会进行熔断。
     * 注意由于统计时间窗口是分钟级别的，若 timeWindow 小于 60s，则结束熔断状态后仍可能再进入熔断状态。
     */

    private void initSentinelRule() {
        //熔断规则： 5s内调用接口出现异常次数超过1的时候, 进行熔断
        List<DegradeRule> degradeRules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        rule.setResource("queryGoodsInfo");
        rule.setCount(1);
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);//熔断规则
        rule.setTimeWindow(1);
        degradeRules.add(rule);
        DegradeRuleManager.loadRules(degradeRules);
    }

    //通过流控规则来指定允许该资源通过的请求次数，例如下面的代码定义了资源 HelloWorld 每秒最多只能通过 1 个请求
    private void initFlowRules(){
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 1.
        rule.setCount(1);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

}