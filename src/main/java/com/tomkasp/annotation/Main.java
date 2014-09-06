package com.tomkasp.annotation;

import org.quartz.*;

import java.util.Date;

/**
 * Created by tomkasp on 9/5/14.
 */
public class Main {


    public static void main(String[] args) throws JobExecutionException {

        Job job = SampleJob.getInstance();
        job.test();
    }
}
