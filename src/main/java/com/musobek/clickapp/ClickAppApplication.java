package com.musobek.clickapp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//@EnableTransactionManagement

public class ClickAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClickAppApplication.class, args);
    }

}
