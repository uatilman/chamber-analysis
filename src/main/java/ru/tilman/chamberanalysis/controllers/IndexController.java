package ru.tilman.chamberanalysis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.tilman.chamberanalysis.entity.Chamber;
import ru.tilman.chamberanalysis.repository.ChamberRepository;

import java.util.List;
import java.util.Locale;


@Controller
public class IndexController {

    private final static String CHAMBERS_COUNT_ATTRIBUTE = "chambersCount";
    private final ChamberRepository chamberRepository;
    private final MessageSource messageSource;

    @Autowired
    public IndexController(
            @Qualifier("chamberRepository") ChamberRepository chamberRepository,
            MessageSource messageSource
    ) {
        this.chamberRepository = chamberRepository;
        this.messageSource = messageSource;

    }

    @RequestMapping("/")
    public String welcome(Model model, Locale locale) {
        List<Chamber> chamberList = chamberRepository.findAllByOrderByIdAsc();
        model.addAttribute("testModel", messageSource.getMessage("welcome.message", new Object[]{}, locale));
        model.addAttribute("chambers", chamberList);
        String msg = String.format(
                messageSource.getMessage("chambers.title", new Object[]{}, locale),
                chamberList.size());
        model.addAttribute(CHAMBERS_COUNT_ATTRIBUTE, msg);
        return "index";
    }

    @RequestMapping("/hi")
    public String test() {
        return "hi";
    }

    @RequestMapping("/chambers")
    public String chambers(
            @RequestParam(value = "id", defaultValue = "-1") Long id // TODO: 07.08.18 test def value null
    ) {
        if (id != -1) return "view";
        return "chambers";
    }

}
