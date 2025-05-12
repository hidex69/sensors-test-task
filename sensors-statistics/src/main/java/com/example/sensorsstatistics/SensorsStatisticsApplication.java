package com.example.sensorsstatistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SensorsStatisticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SensorsStatisticsApplication.class, args);
    }

}
