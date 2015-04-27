package com.tomkasp.api;

import com.tomkasp.entities.QuartzJobDetails;
import com.tomkasp.repository.QuartzJobDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Jobs {

    private final QuartzJobDetailsRepository quartzJobDetailsRepository;

    @Autowired
    public Jobs(QuartzJobDetailsRepository quartzJobDetailsRepository) {
        this.quartzJobDetailsRepository = quartzJobDetailsRepository;
    }

    @RequestMapping("/jobdetails")
    public List<QuartzJobDetails> getAllQuartzJobDetails(){
        return quartzJobDetailsRepository.findAll();
    }

}
