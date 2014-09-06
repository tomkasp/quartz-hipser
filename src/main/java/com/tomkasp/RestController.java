package com.tomkasp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by tomkasp on 9/2/14.
 */
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    DataSource dataSource;

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping("/rest")
    public String getAllSchedulers(){

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List list = jdbcTemplate.queryForList("select * from QRTZ_SCHEDULER_STATE");
        for (Object o : list) {
            System.out.println("Value of the obj:" + o);
        }


        SchedulerRepository schedulerRepository;
        schedulerRepository = applicationContext.getBean(SchedulerRepository.class);


        List<SchedulerState> schedulers = schedulerRepository.findAll();
    System.out.println("check entity");
        for (SchedulerState scheduler : schedulers) {
            System.out.println("scheduler value:" + scheduler);
        }

        return list.toString();
    }
}
