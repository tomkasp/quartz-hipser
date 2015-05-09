package com.tomkasp.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.tomkasp.QuartzHipsterApplication;
import com.tomkasp.config.QuartzConfig;
import com.tomkasp.config.TriggersConfig;
import com.tomkasp.entities.trigers.QuartzTriggers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    static final Logger LOG = LoggerFactory.getLogger(TriggersAPTest.class);

    @Value("${local.server.port}")
    int port;

    String triggerGroup = "DEFAULT";
    String triggerNewState = Trigger.TriggerState.PAUSED.toString();

    QuartzTriggers quartzTriggers;
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    Scheduler scheduler;

    @Before
    public void setUp() {
        RestAssured.port = port;

        quartzTriggers = new QuartzTriggers()
                .schedulerName(QuartzConfig.SCHEDULER_NAME)
                .triggerName(TriggersConfig.TRIGGER_NAME)
                .triggerGroup(triggerGroup)
                .triggerState(triggerNewState);
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
    public void pause_trigger() throws SchedulerException, JsonProcessingException {
        pauseTrigger();
        assureTriggerIsPaused();
    }



    @Test
    public void resume_trigger() throws SchedulerException, JsonProcessingException {
        pauseTrigger();
        assureTriggerIsPaused();
        triggerNewState = Trigger.TriggerState.NORMAL.toString();
        quartzTriggers.triggerState(triggerNewState);
        String messageBody = objectMapper.writeValueAsString(quartzTriggers);
        LOG.info("Trigger json to resume : {}", messageBody);
        given()
                .contentType("application/json")
                .body(messageBody)
                .when()
                .put("/quartz/triggers")
                .then()
                .statusCode(200);

        Trigger.TriggerState triggerState = scheduler.getTriggerState(new TriggerKey(TriggersConfig.TRIGGER_NAME, triggerGroup));
        assertTrue(triggerState.equals(Trigger.TriggerState.NORMAL));
    }

    private void assureTriggerIsPaused() throws SchedulerException {
        Trigger.TriggerState triggerState = scheduler.getTriggerState(new TriggerKey(TriggersConfig.TRIGGER_NAME, triggerGroup));
        assertTrue(triggerState.equals(Trigger.TriggerState.PAUSED));
    }

    private void pauseTrigger() throws JsonProcessingException {
        given()
                .contentType("application/json")
                .body(objectMapper.writeValueAsString(quartzTriggers))
        .when()
                .put("/quartz/triggers")
        .then()
                .statusCode(200);
    }
}
