package com.tomkasp.api;

import com.tomkasp.entities.trigers.QuartzCronTriggers;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/quartz/crontriggers")
public class CronTriggersApi {

    static final Logger LOG = LoggerFactory.getLogger(CronTriggersApi.class);

    @RequestMapping(method = RequestMethod.PUT)
    public void updateCronTrigger(@RequestBody QuartzCronTriggers quartzCronTriggers) throws SchedulerException {
        LOG.debug("Attempt to update cron trigger {}", quartzCronTriggers);
    }

}
