package com.tomkasp.api;

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

@RestController
@RequestMapping("/quartz/triggers")
public class TriggersActions {

    static final Logger LOG = LoggerFactory.getLogger(TriggersActions.class);

    private final Scheduler scheduler;

    @Autowired
    public TriggersActions(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateTrigger(@RequestBody QuartzTriggers quartzTriggers) throws SchedulerException {
        LOG.debug("Trigger to update {}", quartzTriggers);
//        checkTriggerAndDoAction(quartzTriggers);
//        if(Trigger.TriggerState.PAUSED quartzTriggers.getTriggerState() )
//        LOG.info("pausing trigger with a key: {}", triggerKey);
//        scheduler.pauseTrigger(triggerKey);
    }

    private void checkTriggerAndDoAction(QuartzTriggers quartzTriggers) {
//        TriggerKey triggerKey = new TriggerKey(quartzTriggers.getTriggerName(), quartzTriggers.getTriggerGroup());
//        String triggerState = quartzTriggers.getTriggerState();
//        if(isStateHasChanged(triggerKey, triggerState)){
//
//        }
//        if(Trigger.TriggerState.PAUSED.equals(triggerState)){
//            pauseTrigger();
//        }
    }

    private void isStateHasChanged(TriggerKey triggerKey, Trigger.TriggerState triggerState){

    }

    private void pauseTrigger(){

    }
}
