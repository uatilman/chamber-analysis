package ru.tilman.chambers.usemicro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class MicroServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroServiceApplication.class, args);
    }

    @FeignClient("enterprise")
    interface NameService {
        @RequestMapping("/rest/test")
        String getName();
    }

}
