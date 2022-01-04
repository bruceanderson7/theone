package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: Bruce Shen
 * @DataTime： 2022/1/4 3:09 PM
 **/
@Configuration
public class okHttpConfig {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
