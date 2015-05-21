package com.tomkasp.api;


import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/quartz/jobs")
public class JobApi {

    static final Logger LOG = LoggerFactory.getLogger(JobApi.class);

    private final Scheduler scheduler;

    @Autowired
    public JobApi(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @RequestMapping(value = "/{jobgroup}/{jobname}", method = RequestMethod.DELETE)
    public void deleteJob(@PathVariable String jobgroup, @PathVariable String jobname) throws SchedulerException {
        JobKey jobKey = new JobKey(jobname, jobgroup);
        scheduler.deleteJob(jobKey);
    }

    //returns job
    @RequestMapping(value = "/{jobgroup}/{jobname}", method = RequestMethod.GET)
    public JobDetail getJob(@PathVariable String jobgroup, @PathVariable String jobname) throws SchedulerException {
        JobKey jobKey = new JobKey(jobname, jobgroup);
        return scheduler.getJobDetail(jobKey);
    }

    //returns job
    @RequestMapping(value = "/{jobgroup}/{jobname}/paused", method = RequestMethod.GET)
    public void getPausedInfo() {
//            scheduler.pauseJob();
    }

    @RequestMapping(value = "/{jobgroup}/{jobname}/pause", method = RequestMethod.PUT)
    public void setPausedInfo(@PathVariable String jobgroup, @PathVariable String jobname) throws SchedulerException {
        LOG.info("Attempt to pause job {} {}", jobgroup, jobname);
        JobKey jobKey = new JobKey(jobname, jobgroup);
        scheduler.pauseJob(jobKey);
    }

    @RequestMapping(value = "/{jobgroup}/{jobname}/pause", method = RequestMethod.DELETE)
    public void deletePauseInfo(@PathVariable String jobgroup, @PathVariable String jobname) throws SchedulerException {
        JobKey jobKey = new JobKey(jobname, jobgroup);
        scheduler.resumeJob(jobKey);
    }
}
