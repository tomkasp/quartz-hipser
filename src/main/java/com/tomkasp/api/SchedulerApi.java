package com.tomkasp.api;

import com.tomkasp.entities.SchedulerState;
import com.tomkasp.repository.SchedulerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SchedulerApi {

    private SchedulerRepository schedulerRepository;

    @Autowired
    public SchedulerApi(SchedulerRepository schedulerRepository) {
        this.schedulerRepository = schedulerRepository;
    }

    @ResponseBody
    @RequestMapping("/schedulers")
    public List<SchedulerState> getAllSchedulers() {
        List<SchedulerState> schedulers = schedulerRepository.findAll();
        return schedulers;
    }
}
