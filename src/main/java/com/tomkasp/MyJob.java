package com.tomkasp;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

/**
 * Created by tomkasp on 9/2/14.
 */
@Service
public class MyJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {
        System.out.println("Message: Hello World");
    }
}