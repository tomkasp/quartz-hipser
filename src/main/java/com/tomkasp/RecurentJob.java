package com.tomkasp;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by tomkasp on 9/2/14.
 */
public class RecurentJob implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        final JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        final String jobData = jobDataMap.getString("jobData");
        System.out.println("Hello... [{}]" + jobData);
    }

}
