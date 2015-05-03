package com.tomkasp.api;

import com.jayway.restassured.RestAssured;
import com.tomkasp.QuartzHipsterApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = QuartzHipsterApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class TriggersTest {

    @Value("${local.server.port}")
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void get_all_fired_triggers() {

//        when()
//                .get("/quartz/jobdetails");
//
        when()
                .get("/quartz/triggers/cron")
                .then()
                .body("schedulerName", hasItems("quartzScheduler"))
                .body("triggerName", hasItem("processMyJobTrigger"));

    }
}
