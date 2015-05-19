package com.tomkasp.api;


import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/quartz/jobs")
public class JobApi {

    private final Scheduler scheduler;

    @Autowired
    public JobApi(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @RequestMapping(value = "/{jobgroup}/{jobname}")
    public void deleteJob(@PathVariable String jobgroup, @PathVariable String jobname) throws SchedulerException {
        JobKey jobKey = new JobKey(jobname, jobgroup);
        scheduler.deleteJob(jobKey);

    }

}
