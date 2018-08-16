package ru.tilman.chambers.usemicro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("rest")
@RestController
public class Controller {

    private final MicroServiceApplication.NameService nameService;

    @Autowired(required = false)
    public Controller(MicroServiceApplication.NameService nameService) {
        this.nameService = nameService;
    }

    @RequestMapping("/testmicro")
    public String test() {
        return nameService.getName();
    }

}
