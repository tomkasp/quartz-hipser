package com.tomkasp.api;

import com.jayway.restassured.RestAssured;
import com.tomkasp.QuartzHipsterApplication;
import com.tomkasp.config.QuartzConfig;
import com.tomkasp.config.TriggersConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = QuartzHipsterApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class TriggersAPTest {

    @Value("${local.server.port}")
    int port;

    String triggerAsJson = "";
    String triggerGroup = "DEFAULT";
    String triggerNewState = "PAUSED";

    @Autowired
    Scheduler scheduler;

    @Before
    public void setUp() {
        RestAssured.port = port;

        triggerAsJson = "{\n" +
                "  \"jobName\": \"string\",\n" +
                "  \"jobGroup\": \"string\",\n" +
                "  \"jobDate\": [\n" +
                "    null\n" +
                "  ],\n" +
                "  \"prevFireTime\": 0,\n" +
                "  \"triggerType\": \"string\",\n" +
                "  \"misfireInstr\": 0,\n" +
                "  \"triggerName\": \"" + TriggersConfig.TRIGGER_NAME + "\",\n" +
                "  \"triggerGroup\": \"" + triggerGroup + "\",\n" +
                "  \"endTime\": 0,\n" +
                "  \"nextFireTime\": 0,\n" +
                "  \"schedulerName\": \"string\",\n" +
                "  \"triggerState\": \"" + triggerNewState + "\",\n" +
                "  \"priority\": 0,\n" +
                "  \"startTime\": 0,\n" +
                "  \"description\": \"string\",\n" +
                "  \"calendarName\": \"string\"\n" +
                "}";
    }


    @Test
    public void get_all_fired_triggers() {

        when()
                .get("/quartz/triggers/cron")
                .then()
                .body("schedulerName", hasItems(QuartzConfig.SCHEDULER_NAME))
                .body("triggerName", hasItem(TriggersConfig.TRIGGER_NAME));

    }

    @Test
    public void pause_trigger() throws SchedulerException {

        given()
                .contentType("application/json")
                .body(triggerAsJson)
        .when()
                .put("/quartz/triggers")
        .then()
                .statusCode(200);

        Trigger.TriggerState triggerState = scheduler.getTriggerState(new TriggerKey(TriggersConfig.TRIGGER_NAME, triggerGroup));
        assertTrue(triggerState.equals(Trigger.TriggerState.PAUSED));
    }

}
