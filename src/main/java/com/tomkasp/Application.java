package com.tomkasp;


import com.tomkasp.customannotation.BeanTomasz;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.quartz.spi.JobFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {


    @Bean
    public JobFactory jobFactory() {
        return null;
    }



    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }


    public static void main(String[] args) {


        ConfigurableApplicationContext context = SpringApplication.run(Application.class);
        BeanTomasz beanTomasz =  (BeanTomasz)context.getBean(BeanTomasz.class);
        beanTomasz.foo();
//        CustomerRepository repository = context.getBean(CustomerRepository.class);
//        SchedulerRepository schedulerRepository = context.getBean(SchedulerRepository.class);
//
//
//        DataSource dataSource = (DataSource) context.getBean("dataSource");
//        System.out.println("!!!!DATA SOURCE VALUE:" + dataSource);
//
//        List<SchedulerState> schedulers = schedulerRepository.findAll();
//
//        for (SchedulerState scheduler : schedulers) {
//            System.out.println("scheduler value:" + scheduler);
//        }
//
//        context.close();
    }
}
