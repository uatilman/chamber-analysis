package ru.tilman.chambers.chamber_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ChamberMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChamberMicroserviceApplication.class, args);
    }

}
