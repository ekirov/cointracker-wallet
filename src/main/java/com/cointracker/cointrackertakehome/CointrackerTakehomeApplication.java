package com.cointracker.cointrackertakehome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CointrackerTakehomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CointrackerTakehomeApplication.class, args);
    }

}
