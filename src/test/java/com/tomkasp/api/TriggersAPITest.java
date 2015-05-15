package com.tomkasp.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.tomkasp.QuartzHipsterApplication;
import com.tomkasp.config.QuartzConfig;
import com.tomkasp.config.TriggersConfig;
import com.tomkasp.entities.trigers.QuartzCronTriggers;
import com.tomkasp.entities.trigers.QuartzTriggers;
import com.tomkasp.entities.trigers.QuartzTriggersId;
import com.tomkasp.repository.QuartzCronTriggersRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.ParseException;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = QuartzHipsterApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class TriggersAPITest {

    static final Logger LOG = LoggerFactory.getLogger(TriggersAPITest.class);

    @Value("${local.server.port}")
    int port;

    String triggerGroup = "DEFAULT";
    String triggerNewState = Trigger.TriggerState.PAUSED.toString();

    QuartzTriggers quartzTriggers;
    QuartzCronTriggers quartzCronTriggers;
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    Scheduler scheduler;

    @Autowired
    QuartzCronTriggersRepository quartzCronTriggersRepository;

    @Before
    public void setUp() {
        RestAssured.port = port;

        quartzTriggers = new QuartzTriggers()
                .schedulerName(QuartzConfig.SCHEDULER_NAME)
                .triggerName(TriggersConfig.TRIGGER_NAME)
                .triggerGroup(triggerGroup)
                .triggerState(triggerNewState);

        quartzCronTriggers = new QuartzCronTriggers()
                .schedulerName(QuartzConfig.SCHEDULER_NAME)
                .triggerName(TriggersConfig.TRIGGER_NAME)
                .triggerGroup(triggerGroup)
                .cronExpression(TriggersConfig.CRON_EXPRESSION);
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
        assuredTriggerIsPaused();
    }

    @Test
    public void resume_trigger() throws SchedulerException, JsonProcessingException {
        pauseTrigger();
        assuredTriggerIsPaused();

        //change trigger state to NORMAL - it means resume
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

        //Confirm request
        Trigger.TriggerState triggerState = scheduler.getTriggerState(new TriggerKey(TriggersConfig.TRIGGER_NAME, triggerGroup));
        assertTrue(triggerState.equals(Trigger.TriggerState.NORMAL));
    }

    @Test
    public void incorrect_trigger_status_value() throws JsonProcessingException {
        //change trigger state to NORMAL - it means resume
        triggerNewState = "incorrect state value";
        quartzTriggers.triggerState(triggerNewState);
        String messageBody = objectMapper.writeValueAsString(quartzTriggers);
        LOG.info("Trigger json to resume : {}", messageBody);

        given()
                .contentType("application/json")
                .body(messageBody)
                .when()
                .put("/quartz/triggers")
                .then()
                .statusCode(400);
    }

    @Test
    public void reschedule_cron_trigger() throws SchedulerException, JsonProcessingException, ParseException {
        final TriggerKey triggerKey = new TriggerKey(TriggersConfig.TRIGGER_NAME, triggerGroup);
        final String cronExpression = "1 0/1 * * * ?";
        quartzCronTriggers.setCronExpression(cronExpression);
        String triggerJSON = objectMapper.writeValueAsString(quartzCronTriggers);
        LOG.info("Trigger to reschedule : {}", triggerJSON);

        given()
                .contentType("application/json")
                .body(triggerJSON)
                .when()
                .put("/quartz/triggers/cron")
                .then()
                .statusCode(200);

        CronTriggerImpl triggerAfterCronExpressionUpdate = (CronTriggerImpl) scheduler.getTrigger(triggerKey);
        assertTrue(cronExpression.equals(triggerAfterCronExpressionUpdate.getCronExpression()));
    }

    @Test
    public void create_cron_trigger() throws JsonProcessingException {
        final String newTriggerName = "newTrigger";
        quartzCronTriggers.setTriggerName(newTriggerName);
        String triggerJSON = objectMapper.writeValueAsString(quartzCronTriggers);
        given()
                .contentType("application/json")
                .body(triggerJSON)
                .when()
                .post("/quartz/triggers/cron")
                .then()
                .statusCode(200);

        final QuartzTriggersId quartzTriggersId = new QuartzTriggersId();
        quartzTriggersId.setSchedulerName(QuartzConfig.SCHEDULER_NAME);
        quartzTriggersId.setTriggerGroup(triggerGroup);
        quartzTriggersId.setTriggerName(newTriggerName);
        assertNotNull(quartzCronTriggersRepository.findOne(quartzTriggersId));
    }

    @Test
    public void delete_trigger(){
        given()
                .contentType("application/json")
                .when()
                .delete("/quartz/triggers/cron/" + triggerGroup + "/" + TriggersConfig.TRIGGER_NAME)
                .then()
                .statusCode(200);
        final QuartzTriggersId quartzTriggersId = new QuartzTriggersId();
        quartzTriggersId.setSchedulerName(QuartzConfig.SCHEDULER_NAME);
        quartzTriggersId.setTriggerGroup(triggerGroup);
        quartzTriggersId.setTriggerName(TriggersConfig.TRIGGER_NAME);
        assertNull(quartzCronTriggersRepository.findOne(quartzTriggersId));
    }

    private void assuredTriggerIsPaused() throws SchedulerException {
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
