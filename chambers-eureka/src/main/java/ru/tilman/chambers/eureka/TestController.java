package ru.tilman.chambers.eureka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope // curl -X POST http://localhost:8761/actuator/refresh -d {} -H "Content-Type: application/json"
public class TestController {


    @Value("${developer.email}")
    public String test;

    @RequestMapping(value = "/test")
    public String test() {
        return test;
    }

}
