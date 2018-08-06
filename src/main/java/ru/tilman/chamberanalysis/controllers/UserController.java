package ru.tilman.chamberanalysis.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.tilman.chamberanalysis.entity.security.Role;
import ru.tilman.chamberanalysis.entity.security.User;
import ru.tilman.chamberanalysis.repository.RoleRepository;
import ru.tilman.chamberanalysis.repository.UserRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Controller
public class UserController {

    private final String MESSAGE_ATTRIBUTE = "message";
    private final String ERROR_MESSAGE_ATTRIBUTE = "errMessage";
    private final String USER_ATTRIBUTE = "user";
    private final String USERS_ATTRIBUTE = "users";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MessageSource messageSource;

    @Autowired
    public UserController(
            @Qualifier("userRepository") UserRepository userRepository,
            @Qualifier("roleRepository") RoleRepository roleRepository,
            MessageSource messageSource) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
        this.roleRepository = roleRepository;
    }

    @RequestMapping(value = "registration", method = RequestMethod.GET)
    public String registrationForm(Model uiModel) {
        User user = new User();
        uiModel.addAttribute(USER_ATTRIBUTE, user);
        return "registration";
    }

//    @RequestMapping(value = "admin/users", method = RequestMethod.GET)
//    public String showUsers(Model uiModel, User user) {
//
//        uiModel.addAttribute(USERS_ATTRIBUTE, userRepository.findAll());
//        return "users";
//    }

    //принимает данные формы
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(
            Model uiModel,
            @ModelAttribute("user") @Valid User user,
            BindingResult bindingResult,
            RedirectAttributes redAttributes,
            Locale locale) {

        if (bindingResult.hasErrors()) {
            uiModel.addAttribute(
                    ERROR_MESSAGE_ATTRIBUTE,
                    messageSource.getMessage("user_create_fail", new Object[]{}, locale));
            return "registration";
        }

        if (userRepository.findByLogin(user.getLogin()) != null) {

            uiModel.addAttribute(
                    MESSAGE_ATTRIBUTE,
                    messageSource.getMessage("user_login_exist", new Object[]{}, locale));
            return "registration";

        }

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setEnabled(true);
        Role role = roleRepository.findByName("user");
        List<Role> roleList = new ArrayList<>();
        roleList.add(role);
        user.setRoleList(roleList);
        userRepository.save(user);

        return "redirect:/";
    }

}
