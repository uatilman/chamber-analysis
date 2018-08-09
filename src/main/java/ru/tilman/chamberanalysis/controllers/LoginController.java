package ru.tilman.chamberanalysis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

import static ru.tilman.chamberanalysis.utils.MessagesAndModelsAttributes.LOGIN_FAILED;
import static ru.tilman.chamberanalysis.utils.MessagesAndModelsAttributes.MESSAGE;

@Controller
public class LoginController {

    private final MessageSource messageSource;

    @Autowired
    public LoginController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) new SecurityContextLogoutHandler().logout(request, response, auth);
        return "redirect:/chambers";
    }

    @RequestMapping(value = "loginfailed")
    public String loginFailed(RedirectAttributes redirectAttributes, Locale locale) {
        redirectAttributes.addFlashAttribute(
                MESSAGE,
                messageSource.getMessage(LOGIN_FAILED, new Object[]{}, locale));
        return "redirect:/login";
    }

}
