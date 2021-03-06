package com.example.redis.impl;

import com.example.redis.utils.RedisLockUtil;
import com.example.redis.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author wuhao
 * @desc ...
 * @date 2020-12-04 15:40:35
 */
@Service
@Slf4j
public class RedisTestServiceImpl {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisLockUtil redisLock;


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 对文章进行投票
     * 文章评分随着时间流逝而减少 公式 fraction=System.currentTimeMillis()+(60*60*24/standardVotes)*vote
     * 文章存储结构  hash  article:12345 |title   测试标题
     * |link    www.test.com
     * |poster  user:123
     * |time    1233333333
     * |vote    234
     * <p>
     * 存储文章  按发布时间存储  zset  time   article:12345  1607071797287
     * 按评分排序     zset  score   article:12345  23223233
     * <p>
     * 投票记录  set voted  user:1234  user:2343
     */
    //发布一个星期关闭投票
    private Integer ONE_WEEK_IN_SECONDS = 7 * 24 * 60 * 60;
    private Integer SCORE = 432;
    private Integer PAGE_NUMBER = 10;


    public void vote() {
        String userId = "user:12345";
        String article = "article:12345";
        //跟新评分和投票记录,更新投票数
        redisUtil.sSet("voted", userId);
        redisUtil.zincrScore("time", article, SCORE);
        redisUtil.hincr(article, "vote", 1);
        Map map = new HashMap();
        map.put("title", "测试标题");
        map.put("link", "www.test.com");
        map.put("poster", userId);
        map.put("time", System.currentTimeMillis());
        redisUtil.hmset(article, map);
    }

    /**
     * 发布并获取文章
     */
    public void publish() {
        String userId = "user:23465";
        String article = "article:23456";
        redisUtil.sSetAndTime("voted", ONE_WEEK_IN_SECONDS, userId);
        Map map = new HashMap();
        map.put("title", "测试标题2");
        map.put("link", "www.test2.com");
        map.put("poster", userId);
        map.put("time", System.currentTimeMillis());
        map.put("vote", 1);
        redisUtil.hmset(article, map);
        redisUtil.zadd("time", article, System.currentTimeMillis());
        redisUtil.zadd("score", article, System.currentTimeMillis());
    }

    /**
     * 获取最新和评分最高的文章
     */
    public void sort() {
        int start = 0 * PAGE_NUMBER;
        int end = 1 * PAGE_NUMBER;
        Set<Object> time = redisUtil.zrevRange("time", start, end);
        System.out.println(time);
        Set<Object> score = redisUtil.zrevRange("score", start, end);
        System.out.println(score);
    }


    /**
     * pipeline
     */
    public void pipelineTest() {
        List keyList = new ArrayList();
        for (int i = 0; i < 1000; i++) {
            keyList.add("pipeTestKey" + i);
        }
        List result = redisUtil.batchGet(keyList);
        System.out.println(result);
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("key", "pipeTestKey" + i);
            map.put("value", "value" + i);
            list.add(map);
        }
        redisUtil.batchInsert(list, TimeUnit.SECONDS, 300);
        List result_two = redisUtil.batchGet(keyList);
        System.out.println(result_two);
    }


    public void transactionalTest() {
        String userId = "user:12345";
        String article = "article:12345";
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.multi();
        //跟新评分和投票记录,更新投票数
        redisTemplate.opsForSet().add("voted", userId);
        redisTemplate.opsForZSet().incrementScore("time", article, SCORE);
        redisTemplate.opsForHash().increment(article, "vote", 1);
        Map map = new HashMap();
        map.put("title", "测试标题");
        map.put("link", "www.test.com");
        map.put("poster", userId);
        map.put("time", System.currentTimeMillis());
        //设置错误点
//        redisUtil.hmset(article, null);
        redisTemplate.opsForHash().putAll(article, map);
        redisTemplate.exec();
    }

    public void transactionalTest2() {
        String userId = "user:12345";
        String article = "article:12345";
        /*使用 SessionCallback, 在同一个 Redis Connection 中执行事务: 成功执行事务*/
        SessionCallback<Object> callback = new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForValue().set("name", "qinyi");
                operations.opsForValue().set("gender", "male");
                Map map = new HashMap();
                map.put("title", "测试标题");
                map.put("link", "www.test.com");
                map.put("poster", userId);
                map.put("time", System.currentTimeMillis());
                //错误，事务回滚
//                operations.opsForHash().putAll(article, null);
                operations.opsForHash().putAll(article, map);
                return operations.exec();
            }
        };

        // [true, true, true]
        System.out.println(redisTemplate.execute(callback));

    }

    public void lockTest(){
        String token = null;
        try{
            token = redisLock.lock("lock_name", 10000, 11000);
            if(token != null) {
                System.out.println("我拿到了锁哦");
                // 执行业务代码
            } else {
                System.out.println("我没有拿到锁唉");
            }
        } finally {
            if(token!=null) {
                redisLock.unlock("lock_name", token);
                System.out.println("放开锁");
            }
        }
    }

}
