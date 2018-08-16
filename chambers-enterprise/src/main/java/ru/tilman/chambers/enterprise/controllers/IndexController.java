package ru.tilman.chambers.enterprise.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.tilman.chambers.enterprise.entity.Chamber;
import ru.tilman.chambers.enterprise.services.ChamberService;
import ru.tilman.chambers.enterprise.utils.MessagesAndModelsAttributes;

import java.util.List;
import java.util.Locale;

@RefreshScope // curl -X POST http://localhost:8761/actuator/refresh -d {} -H "Content-Type: application/json"
@Controller
public class IndexController {

    private final MessageSource messageSource;
    private final ChamberService chamberService;


    @Value("${info.developer.email}")
    public String test;

    @Autowired
    public IndexController(
            @Qualifier("chamberService") ChamberService chamberService,
            MessageSource messageSource
    ) {
        this.chamberService = chamberService;
        this.messageSource = messageSource;
    }

    @RequestMapping("/")
    public String welcome(Model model, Locale locale) {
        List<Chamber> chamberList = chamberService.getChambersListByOrderByIdAsc();
        model.addAttribute(MessagesAndModelsAttributes.TEST_MODEL, messageSource.getMessage(MessagesAndModelsAttributes.WELCOME_MESSAGE, new Object[]{}, locale));
        model.addAttribute(MessagesAndModelsAttributes.CHAMBERS, chamberList);
        model.addAttribute(MessagesAndModelsAttributes.CHAMBERS_COUNT_ATTRIBUTE,
                String.format(messageSource.getMessage(MessagesAndModelsAttributes.CHAMBERS_TITLE, new Object[]{}, locale), chamberList.size()));
        return "index";
    }

    @RequestMapping("/hi")
    public String test(Model model) {
        model
                .addAttribute(MessagesAndModelsAttributes.CHAMBERS, chamberService.getChambersListByOrderByIdAsc())
                .addAttribute("test", test);
        return "hi";
    }

    @RequestMapping("/chambers")
    public String chambers(
            @RequestParam(value = "id", required = false) Long id
    ) {
        System.out.println(id);
        if (id != null) return "view";
        return "chambers";
    }

}
