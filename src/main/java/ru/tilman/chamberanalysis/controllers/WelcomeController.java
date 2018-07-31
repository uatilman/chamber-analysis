package ru.tilman.chamberanalysis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.tilman.chamberanalysis.repository.ChamberRepository;

import java.util.Map;

@Controller
public class WelcomeController {


    final ChamberRepository chamberRepository;
    // inject via application.properties
    @Value("${welcome.message:test}")
    private String message;

    @Autowired
    public WelcomeController(
            @Qualifier("chamberRepository") ChamberRepository chamberRepository
    ) {
        this.chamberRepository = chamberRepository;
    }

    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
        model.put("testModel", message);
        model.put("chambers", chamberRepository.findAll());
        return "hi";
    }

}
