package com.cuentasservice;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableRabbit

@SpringBootApplication
public class CuentasServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CuentasServiceApplication.class, args);
    }

}
