package ru.tilman.chamberanalysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.ChamberRepository;

@RestController
@SpringBootApplication
public class ChamberAnalysisApplication {



    public static void main(String[] args) {
        SpringApplication.run(ChamberAnalysisApplication.class, args);
    }

    @RequestMapping("/test")
    public String test() {
        return "test";
    }
}
