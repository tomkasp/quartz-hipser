package com.tomkasp.annotation;



import java.lang.reflect.Proxy;

/**
 * Created by tomkasp on 9/5/14.
 */
public class SampleJob implements  Job{

    private static Job instance;

    private SampleJob(){
        
    }
    
    public static Job getInstance() {
        if (instance == null) {

           instance =  (Job)Proxy.newProxyInstance(TraceHandler.class.getClassLoader(), new Class[] {Job.class}, new TraceHandler(new SampleJob()));
        }
        return instance;
    }


    @Override
    @GatherStatistics
    public void test() {

        System.out.println("inside");
    }
}
