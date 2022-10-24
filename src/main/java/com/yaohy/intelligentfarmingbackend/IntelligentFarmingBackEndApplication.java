package com.yaohy.intelligentfarmingbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@MapperScan("com.yaohy.intelligentfarmingbackend.mapper")
@EnableOpenApi
@SpringBootApplication
public class IntelligentFarmingBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntelligentFarmingBackEndApplication.class, args);
    }

}
