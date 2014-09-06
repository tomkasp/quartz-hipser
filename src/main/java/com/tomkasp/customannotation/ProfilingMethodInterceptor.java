package com.tomkasp.customannotation;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.StopWatch;

/**
 * Created by tomkasp on 9/6/14.
 */
public class ProfilingMethodInterceptor implements MethodInterceptor {



    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        final StopWatch stopWatch = new StopWatch(invocation.getMethod().toGenericString());
        stopWatch.start("invocation.proceed()");

        try {
            System.out.println("~~~~~~~~ START METHOD {} ~~~~~~~~" + invocation.getMethod().toGenericString());
            return invocation.proceed();
        } finally {
            stopWatch.stop();
            System.out.println(stopWatch.prettyPrint());
            System.out.println("~~~~~~~~ END METHOD {} ~~~~~~~~" + invocation.getMethod().toGenericString());
        }
    }
}