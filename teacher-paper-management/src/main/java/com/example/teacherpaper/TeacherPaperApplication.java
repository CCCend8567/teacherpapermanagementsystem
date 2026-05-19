package com.example.teacherpaper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TeacherPaperApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeacherPaperApplication.class, args);
    }
}