package com.dsu.industry;

import com.dsu.industry.global.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties(AppProperties.class)
public class Dsu2021ReserveApplication {

    public static void main(String[] args) {
        SpringApplication.run(Dsu2021ReserveApplication.class, args);
    }
}
