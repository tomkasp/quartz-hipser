package com.tomkasp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuartzHipsterApplication {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(QuartzHipsterApplication.class);

        app.run(args);

//        SpringApplication.run(QuartzHipsterApplication.class, args);
    }

}
