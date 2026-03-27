package com.practice.project.airbnb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AirBnbApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirBnbApplication.class, args);
    }

}
