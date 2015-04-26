package com.tomkasp.repository;

import com.tomkasp.entities.QuartzCronTriggers;
import com.tomkasp.entities.QuartzTriggersId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface QuartzCronTriggersRepository extends CrudRepository<QuartzCronTriggers, QuartzTriggersId> {

    @Override
    List<QuartzCronTriggers> findAll();
}
