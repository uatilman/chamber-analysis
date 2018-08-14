package ru.tilman.chamberanalysis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.tilman.chamberanalysis.entity.Chamber;
import ru.tilman.chamberanalysis.services.ChamberService;

import java.util.List;
import java.util.Locale;

import static ru.tilman.chamberanalysis.utils.MessagesAndModelsAttributes.*;


@Controller
public class IndexController {

    private final MessageSource messageSource;
    private final ChamberService chamberService;

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
        model.addAttribute(TEST_MODEL, messageSource.getMessage(WELCOME_MESSAGE, new Object[]{}, locale));
        model.addAttribute(CHAMBERS, chamberList);
        model.addAttribute(CHAMBERS_COUNT_ATTRIBUTE,
                String.format(messageSource.getMessage(CHAMBERS_TITLE, new Object[]{}, locale), chamberList.size()));
        return "index";
    }

    @RequestMapping("/hi")
    public String test(Model model) {
        model.addAttribute(CHAMBERS, chamberService.getChambersListByOrderByIdAsc());
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
