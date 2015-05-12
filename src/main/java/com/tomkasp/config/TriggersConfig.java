package com.tomkasp.config;

import com.tomkasp.annotations.EnableQuartz;
import com.tomkasp.job.MyJob;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.PostConstruct;


@Configuration
@EnableQuartz
public class TriggersConfig {

    static final Logger LOG = LoggerFactory.getLogger(TriggersConfig.class);
    public static final String TRIGGER_NAME = "processMyJobTrigger";
    public static final String CRON_EXPRESSION = "0 0/1 * * * ?";

    @Autowired
    SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    GlobalSchedulerListener globalSchedulerListener;

    @PostConstruct
    public void init() throws SchedulerException {
        schedulerFactoryBean.getObject().getListenerManager().addSchedulerListener(globalSchedulerListener);
        Trigger trigger = processMyJobTrigger().getObject();
        JobDetail job = processMyJob().getObject();
        schedulerFactoryBean.getObject().scheduleJob(job, trigger);
    }

    @Bean
    public JobDetailFactoryBean processMyJob() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(MyJob.class);
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean
    // Configure cron to fire trigger every 1 minute
    public CronTriggerFactoryBean processMyJobTrigger() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(processMyJob().getObject());
        cronTriggerFactoryBean.setCronExpression(CRON_EXPRESSION);
        cronTriggerFactoryBean.getObject();
        cronTriggerFactoryBean.setName(TRIGGER_NAME);
        return cronTriggerFactoryBean;
    }
}

