package com.example.resumeparser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/***
 * @title AppConfig
 * @description
 * @author haoyu99
 * @version 1.0.0
 * @create 2024/7/13 12:21
 **/
@Configuration

public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
