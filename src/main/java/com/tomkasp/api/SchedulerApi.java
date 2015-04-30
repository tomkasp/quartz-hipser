package com.tomkasp.api;

import com.tomkasp.entities.trigers.QuartzCronTriggers;
import com.tomkasp.entities.trigers.QuartzTriggers;
import com.tomkasp.entities.SchedulerState;
import com.tomkasp.repository.QuartzCronTriggersRepository;
import com.tomkasp.repository.QuartzTriggersRepository;
import com.tomkasp.repository.SchedulerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SchedulerApi {

    private final SchedulerRepository schedulerRepository;


    @Autowired
    public SchedulerApi(SchedulerRepository schedulerRepository) {
        this.schedulerRepository = schedulerRepository;
    }

    @RequestMapping(value = "/schedulers", method = RequestMethod.GET)
    public List<SchedulerState> getAllSchedulers() {

        List<SchedulerState> schedulers = schedulerRepository.findAll();
        return schedulers;
    }
}
