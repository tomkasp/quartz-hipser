package com.tomkasp.config;

import com.tomkasp.job.MyJob;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;


@Configuration
public class QuartzConfig {

    static final Logger LOG = LoggerFactory.getLogger(QuartzConfig.class);

    @Autowired
    DataSource dataSource;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public SchedulerFactoryBean quartzScheduler() {
        SchedulerFactoryBean quartzScheduler = new SchedulerFactoryBean();

        quartzScheduler.setQuartzProperties(quartzProperties());
        quartzScheduler.setDataSource(dataSource);
        quartzScheduler.setTransactionManager(transactionManager);
        quartzScheduler.setOverwriteExistingJobs(true);

        // Custom job factory of spring with DI support for @Autowired
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        quartzScheduler.setJobFactory(jobFactory);

        Trigger[] triggers = {
                processMyJobTrigger().getObject()
        };
        quartzScheduler.setTriggers(triggers);

        return quartzScheduler;
    }

    @Bean
    public JobDetailFactoryBean processMyJob() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(MyJob.class);
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean
    // Configure cron to fire trigger every 1 minute
    public CronTriggerFactoryBean processMyJobTrigger() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(processMyJob().getObject());
        cronTriggerFactoryBean.setCronExpression("0 0/1 * * * ?");
        return cronTriggerFactoryBean;
    }

    @Bean
    public Properties quartzProperties() {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("quartz.properties"));
        Properties properties;

        try {
            propertiesFactoryBean.afterPropertiesSet();
            properties = propertiesFactoryBean.getObject();
            LOG.info("Value of the property org.quartz.jobStore.driverDelegateClass {}", properties.getProperty("org.quartz.jobStore.driverDelegateClass"));
        }
        catch (IOException e) {
            throw new RuntimeException("Unable to load quartz.properties", e);
        }
        return properties;
    }

}

