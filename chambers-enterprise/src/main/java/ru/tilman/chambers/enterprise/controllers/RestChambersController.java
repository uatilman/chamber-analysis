package ru.tilman.chambers.enterprise.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tilman.chambers.enterprise.entity.Chamber;
import ru.tilman.chambers.enterprise.services.ChamberFeignService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("rest")
public class RestChambersController {

    public static final String DESC = "DESC";
    public static final String ASC = "ASC";
    public static final String ID = "id";


//    private final ChamberService chamberService;
    private final ChamberFeignService chamberFeignService;

    @Autowired(required = false)
    public RestChambersController(ChamberFeignService chamberFeignService) {
        this.chamberFeignService = chamberFeignService;
    }

//    @Autowired(required = false)
//    public RestChambersController(
//            @Qualifier("chamberService") ChamberService chamberService,
//            ChamberFeignService chamberFeignService
//    ) {
//        this.chamberService = chamberService;
//        this.chamberFeignService = chamberFeignService;
//    }

    @RequestMapping("/test")
    public String test() {
        return new Date().toString();
    }

    @RequestMapping("/getChambers") // TODO: 09.08.18 add required = false instead defaultValue
    public List<Chamber> getChambersList(
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "order", defaultValue = ASC) String order,
            @RequestParam(value = "orderBy", defaultValue = ID) String orderBy,
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return chamberFeignService.getChambers();
    }
}
