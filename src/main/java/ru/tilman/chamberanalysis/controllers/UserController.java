package ru.tilman.chamberanalysis.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.tilman.chamberanalysis.entity.security.User;
import ru.tilman.chamberanalysis.services.RegistrationService;

import javax.validation.Valid;
import java.util.Locale;

import static ru.tilman.chamberanalysis.utils.MessagesAttributes.*;
import static ru.tilman.chamberanalysis.utils.MessagesCodes.CREATE_FAIL;
import static ru.tilman.chamberanalysis.utils.MessagesCodes.LOGIN_EXIST;

@Controller
public class UserController {

//    private final UserRepository userRepository;
//    private final RoleRepository roleRepository;

    private RegistrationService registrationService;
    private final MessageSource messageSource;

    @Autowired
    public UserController(
            @Qualifier("registrationService") RegistrationService registrationService,
//            @Qualifier("userRepository") UserRepository userRepository,
//            @Qualifier("roleRepository") RoleRepository roleRepository,
            MessageSource messageSource
    ) {
//        this.userRepository = userRepository;
        this.messageSource = messageSource;
        this.registrationService = registrationService;
//        this.roleRepository = roleRepository;
    }

    @RequestMapping(value = "registration", method = RequestMethod.GET)
    public String registrationForm(Model uiModel) {
        User user = new User();
        uiModel.addAttribute(USER, user);
        return "registration";
    }

    @RequestMapping(value = "admin/users", method = RequestMethod.GET)
    public String showUsers(Model uiModel, User user) {
        uiModel.addAttribute(USERS, registrationService.findAllUsers());
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
                    ERROR_MESSAGE,
                    messageSource.getMessage(CREATE_FAIL, new Object[]{}, locale));
            return true;
        }
        return false;
    }

    private boolean checkLogin(Model uiModel, @Valid @ModelAttribute("user") User user, Locale locale) {
        if (registrationService.isLoginExist(user.getLogin())) {
            uiModel.addAttribute(
                    MESSAGE,
                    messageSource.getMessage(LOGIN_EXIST, new Object[]{}, locale));
            return true;
        }
        return false;
    }

}
