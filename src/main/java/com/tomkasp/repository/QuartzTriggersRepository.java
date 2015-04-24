package com.tomkasp.repository;


import com.tomkasp.entities.QuartzTriggers;
import com.tomkasp.entities.QuartzTriggersId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuartzTriggersRepository extends CrudRepository<QuartzTriggers, QuartzTriggersId>{

    @Override
    List<QuartzTriggers> findAll();
}
