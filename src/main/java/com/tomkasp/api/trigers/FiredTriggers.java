package com.tomkasp.api.trigers;

import com.tomkasp.entities.trigers.QuartzFiredTriggers;
import com.tomkasp.repository.QuartzFiredTriggersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FiredTriggers {

    private final QuartzFiredTriggersRepository quartzFiredTriggersRepository;

    @Autowired
    public FiredTriggers(QuartzFiredTriggersRepository quartzFiredTriggersRepository) {
        this.quartzFiredTriggersRepository = quartzFiredTriggersRepository;
    }

    @RequestMapping(value = "/firedtriggers")
    public List<QuartzFiredTriggers> getAllFiredTriggers(){
        return quartzFiredTriggersRepository.findAll();
    }
}
