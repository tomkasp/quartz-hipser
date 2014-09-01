package com.tomkasp;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by tomkasp on 9/1/14.
 */
public interface SchedulerRepository extends CrudRepository<SchedulerState, Long> {

    List<SchedulerState> findAll();
}
