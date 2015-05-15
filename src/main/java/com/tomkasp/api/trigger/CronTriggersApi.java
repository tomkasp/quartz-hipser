package com.tomkasp.api.trigger;

import com.tomkasp.entities.trigers.QuartzCronTriggers;
import com.tomkasp.repository.QuartzCronTriggersRepository;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping(value = "/quartz/triggers/cron")
public class CronTriggersApi {

    static final Logger LOG = LoggerFactory.getLogger(CronTriggersApi.class);

    private final Scheduler scheduler;
    private final QuartzCronTriggersRepository quartzCronTriggersRepository;

    @Autowired
    public CronTriggersApi(Scheduler scheduler, QuartzCronTriggersRepository quartzCronTriggersRepository) {
        this.scheduler = scheduler;
        this.quartzCronTriggersRepository = quartzCronTriggersRepository;
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
        quartzCronTriggersRepository.save(quartzCronTriggers);






    }



}
