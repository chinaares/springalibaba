package com.example.interview.oom;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author wuhao
 * @desc -XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m -XX:+PrintGCDetails
 * @date 2021-09-13 10:36:15
 */
public class MetaSpaceOOMDemo {
    static class OOMTest{

    }
    public static void main(String[] args) {
        int i=0;
        try {
            while (true){
                i++;
                Enhancer enhancer=new Enhancer();
                enhancer.setSuperclass(OOMTest.class);
                enhancer.setUseCache(false);
                enhancer.setCallback(new MethodInterceptor() {
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        return methodProxy.invokeSuper(o,args);
                    }
                });
                enhancer.create();
            }
        }catch (Throwable e){
            System.out.println("========="+i+"次后发生了异常");
            e.printStackTrace();
        }

    }
}
