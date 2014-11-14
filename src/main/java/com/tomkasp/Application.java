package com.tomkasp;


import org.quartz.spi.JobFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {


    @Bean
    public JobFactory jobFactory() {
        return null;
    }






    public static void main(String[] args) {


        ConfigurableApplicationContext context = SpringApplication.run(Application.class);
//        BeanTomasz beanTomasz =  (BeanTomasz)context.getBean(BeanTomasz.class);
//        beanTomasz.foo();
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
