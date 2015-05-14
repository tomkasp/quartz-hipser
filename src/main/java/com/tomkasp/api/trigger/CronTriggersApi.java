package com.tomkasp.api.trigger;

import com.tomkasp.entities.trigers.QuartzCronTriggers;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping(value = "/quartz/crontriggers")
public class CronTriggersApi {

    static final Logger LOG = LoggerFactory.getLogger(CronTriggersApi.class);

    private final Scheduler scheduler;
    private final ApplicationContext applicationContext;

    @Autowired
    public CronTriggersApi(Scheduler scheduler, ApplicationContext applicationContext) {
        this.scheduler = scheduler;
        this.applicationContext = applicationContext;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateCronTrigger(@RequestBody QuartzCronTriggers quartzCronTriggers) throws SchedulerException, ParseException {
        LOG.debug("Attempt to update cron trigger {}", quartzCronTriggers);

        final TriggerKey triggerKey = new TriggerKey(quartzCronTriggers.getTriggerName(), quartzCronTriggers.getTriggerGroup());
        CronTriggerImpl trigger = (CronTriggerImpl) scheduler.getTrigger(triggerKey);
        trigger.setCronExpression(quartzCronTriggers.getCronExpression());
        scheduler.rescheduleJob(triggerKey, trigger);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createCronTrigger(@RequestBody QuartzCronTriggers quartzCronTriggers) throws SchedulerException, ParseException {
        LOG.debug("Attempt to create cron trigger {}", quartzCronTriggers);

        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setCronExpression("1 0/1 * * * ?");
        cronTriggerFactoryBean.getObject();
        cronTriggerFactoryBean.setName("tomasz");

        AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
        beanFactory.autowireBean(cronTriggerFactoryBean);
        beanFactory.initializeBean(quartzCronTriggers, "tomaszBean");




    }



}
