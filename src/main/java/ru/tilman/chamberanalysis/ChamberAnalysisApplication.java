package ru.tilman.chamberanalysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

//@RestController
@SpringBootApplication
public class ChamberAnalysisApplication {


    public static void main(String[] args) {
        SpringApplication.run(ChamberAnalysisApplication.class, args);
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasename("messages");
        return messageSource;
    }

/*    @RequestMapping("/test")
    public String test() {
        return "test";
    }*/
}
