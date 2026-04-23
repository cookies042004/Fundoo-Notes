package com.fundoonotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableCaching
@EnableJms
public class FundooNotesApplication {

    public static void main(String[] args) {
        // UC1:Application entry point
        SpringApplication.run(FundooNotesApplication.class, args);
    }
}
