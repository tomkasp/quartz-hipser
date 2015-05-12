package com.tomkasp.api;

import com.tomkasp.entities.trigers.QuartzCronTriggers;
import com.tomkasp.entities.trigers.QuartzTriggers;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/quartz/triggers")
public class TriggersApi {

    static final Logger LOG = LoggerFactory.getLogger(TriggersApi.class);

    private final Scheduler scheduler;

    @Autowired
    public TriggersApi(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateTrigger(@Valid @RequestBody QuartzTriggers quartzTriggers) throws SchedulerException {
        LOG.debug("Attempt to update trigger {}", quartzTriggers);
        checkTriggerAndDoAction(quartzTriggers);
    }

    private void checkTriggerAndDoAction(QuartzTriggers quartzTriggers) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(quartzTriggers.getTriggerName(), quartzTriggers.getTriggerGroup());
        Trigger.TriggerState newTriggerState = Trigger.TriggerState.valueOf(quartzTriggers.getTriggerState());
        if (isStateToUpdate(newTriggerState, triggerKey)) {
            LOG.info("Trigger with an id: {} is going to be updated with a new state: {}", triggerKey, newTriggerState);
            changeTriggerState(triggerKey, newTriggerState);
        }
    }

    private Boolean isStateToUpdate(Trigger.TriggerState newTriggerState, TriggerKey triggerKey) throws SchedulerException {
        //new State is not equals to current state - we need to update
        return !scheduler.getTriggerState(triggerKey).equals(newTriggerState);
    }

    private void changeTriggerState(TriggerKey triggerKey, Trigger.TriggerState triggerState) throws SchedulerException {
        switch (triggerState) {
            case PAUSED:
                scheduler.pauseTrigger(triggerKey);
                break;
            case NORMAL:
                scheduler.resumeTrigger(triggerKey);
                break;
            default:
                LOG.warn("wrong trigger state : {}", triggerState);
                break;

        }

    }
}
