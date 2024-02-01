package com.housekeeperispurchase;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan({"com.housekeeperispurchase.mapper"})
@SpringBootApplication
public class HouApplication {

    public static void main(String[] args) {
        SpringApplication.run(HouApplication.class, args);
    }

}
