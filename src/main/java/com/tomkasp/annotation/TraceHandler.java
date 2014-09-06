package com.tomkasp.annotation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by tomkasp on 9/5/14.
 */
public class TraceHandler implements InvocationHandler {

    private Object target;

    public TraceHandler(Object target) {
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Method[] m = target.getClass().getDeclaredMethods();

        for(Method met : m){
            GatherStatistics a = met.getAnnotation(GatherStatistics.class);
            if(a != null){
                System.out.println("test");
            }
        }


            System.out.println("before");

        Object ret = method.invoke(target);
        System.out.println("after");

        return ret;

    }
}
