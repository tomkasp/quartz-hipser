package com.tomkasp;

import com.tomkasp.config.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuartzHipsterApplication {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(QuartzHipsterApplication.class);
        app.setAdditionalProfiles(Constants.SPRING_PROFILE_DEVELOPMENT);
        app.run(args);

//        SpringApplication.run(QuartzHipsterApplication.class, args);
    }

}
