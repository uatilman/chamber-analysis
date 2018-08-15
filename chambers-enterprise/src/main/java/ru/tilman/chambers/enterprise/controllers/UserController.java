package ru.tilman.chambers.enterprise.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.tilman.chambers.enterprise.entity.security.User;
import ru.tilman.chambers.enterprise.services.RegistrationService;
import ru.tilman.chambers.enterprise.utils.MessagesAndModelsAttributes;
import ru.tilman.chambers.enterprise.utils.MessagesCodes;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class UserController {

    private final MessageSource messageSource;
    private RegistrationService registrationService;

    @Autowired
    public UserController(
            @Qualifier("registrationService") RegistrationService registrationService,
            MessageSource messageSource
    ) {
        this.messageSource = messageSource;
        this.registrationService = registrationService;
    }

    @RequestMapping(value = "registration", method = RequestMethod.GET)
    public String registrationForm(Model uiModel) {
        User user = new User();
        uiModel.addAttribute(MessagesAndModelsAttributes.USER, user);
        return "registration";
    }

    @RequestMapping(value = "admin/users", method = RequestMethod.GET)
    public String showUsers(Model uiModel, User user) {
        uiModel.addAttribute(MessagesAndModelsAttributes.USERS, registrationService.findAllUsers());
        return "users";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(
            Model uiModel,
            @ModelAttribute("user") @Valid User user,
            BindingResult bindingResult,
            Locale locale
    ) {
        if (checkErrors(uiModel, bindingResult, locale)) return "registration";
        if (checkLogin(uiModel, user, locale)) return "registration";
        registrationService.createUser(user);
        return "redirect:/";
    }

    private boolean checkErrors(Model uiModel, BindingResult bindingResult, Locale locale) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute(
                    MessagesAndModelsAttributes.ERROR_MESSAGE,
                    messageSource.getMessage(MessagesCodes.CREATE_FAIL, new Object[]{}, locale));
            return true;
        }
        return false;
    }

    private boolean checkLogin(Model uiModel, User user, Locale locale) {
        if (registrationService.isLoginExist(user.getLogin())) {
            uiModel.addAttribute(
                    MessagesAndModelsAttributes.MESSAGE,
                    messageSource.getMessage(MessagesCodes.LOGIN_EXIST, new Object[]{}, locale));
            return true;
        }
        return false;
    }

}
