package com.example.week3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing // 시간 자동 변경이 가능하도록 합니다.
@SpringBootApplication
public class Week3Application {

    public static void main(String[] args) {
        SpringApplication.run(Week3Application.class, args);
    }

}