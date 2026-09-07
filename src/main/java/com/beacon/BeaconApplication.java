package com.beacon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BeaconApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeaconApplication.class, args);
    }
}
