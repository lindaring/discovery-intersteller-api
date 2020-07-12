package com.discovery;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class DiscoveryInterstellerApi {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryInterstellerApi.class, args);
    }
}
