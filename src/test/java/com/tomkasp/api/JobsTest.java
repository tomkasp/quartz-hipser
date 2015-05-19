package com.tomkasp.api;

import com.jayway.restassured.RestAssured;
import com.tomkasp.QuartzHipsterApplication;
import com.tomkasp.config.QuartzConfig;
import com.tomkasp.config.TriggersConfig;
import com.tomkasp.entities.QuartzJobDetailsId;
import com.tomkasp.repository.QuartzJobDetailsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = QuartzHipsterApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class JobsTest {

    @Value("${local.server.port}")
    int port;

    public final String JOB_GROUP = "DEFAULT";

    @Autowired
    QuartzJobDetailsRepository quartzJobDetailsRepository;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void get_all_jobs() {

        when()
                .get("/quartz/jobdetails")
        .then()
                .body("schedulerName", hasItems("quartzScheduler"))
                .body("jobName", hasItem("processMyJob"));
    }

    @Test
    public void delete_job(){
        when()
                .delete("/quartz/jobs/" + JOB_GROUP + "/" + TriggersConfig.JOB_NAME)
                .then()
                .statusCode(RestApiHttpStatus.OK.getStatusCode());
        QuartzJobDetailsId quartzJobDetailsId = new QuartzJobDetailsId();
        quartzJobDetailsId.setSchedulerName(QuartzConfig.SCHEDULER_NAME);
        quartzJobDetailsId.setJobGroup(JOB_GROUP);
        quartzJobDetailsId.setJobName(TriggersConfig.JOB_NAME);

        assertNull(quartzJobDetailsRepository.findOne(quartzJobDetailsId));
    }

    public void pause_job(){

    }

    public void pause_jobs(){

    }

    public void schedule_job(){

    }

    public void add_job(){

    }

    public void check_if_job_exists(){

    }

}
