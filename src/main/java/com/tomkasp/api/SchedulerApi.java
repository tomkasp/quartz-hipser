package com.tomkasp.api;

import com.tomkasp.entities.QuartzTriggers;
import com.tomkasp.entities.SchedulerState;
import com.tomkasp.repository.QuartzTriggersRepository;
import com.tomkasp.repository.SchedulerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SchedulerApi {

    private final SchedulerRepository schedulerRepository;
    private final QuartzTriggersRepository quartzTriggersRepository;

    @Autowired
    public SchedulerApi(SchedulerRepository schedulerRepository, QuartzTriggersRepository quartzTriggersRepository) {
        this.schedulerRepository = schedulerRepository;
        this.quartzTriggersRepository = quartzTriggersRepository;
    }

    @RequestMapping("/schedulers")
    public List<SchedulerState> getAllSchedulers() {

        List<SchedulerState> schedulers = schedulerRepository.findAll();
        return schedulers;
    }

    @RequestMapping("/triggers")
    public List<QuartzTriggers> getAllTriggers(){

        List<QuartzTriggers> quartzTriggers = quartzTriggersRepository.findAll();
        return quartzTriggers;
    }


}
