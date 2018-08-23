package ru.tilman.chambers.enterprise.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tilman.chambers.enterprise.entity.Chamber;
import ru.tilman.chambers.enterprise.services.ChamberFeignService;
import ru.tilman.chambers.enterprise.services.ChamberService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("rest")
public class RestChambersController {

    public static final String DESC = "DESC";
    public static final String ASC = "ASC";
    public static final String ID = "id";

    private final ChamberFeignService chamberFeignService;
    private final ChamberService chamberService;

    // TODO: 23.08.18 чтото сломалось в интеграции
//    private CashMessageGateway cashMessageGateway;
//    private CashMessageHTTPGateway cashMessageHTTPGateway;

    @Autowired(required = false)
    public RestChambersController(ChamberFeignService chamberFeignService, ChamberService chamberService) {
        this.chamberFeignService = chamberFeignService;
        this.chamberService = chamberService;
    }

//    @Autowired(required = false)
//    public RestChambersController(ChamberFeignService chamberFeignService, CashMessageGateway cashMessageGateway, CashMessageHTTPGateway cashMessageHTTPGateway) {
//        this.chamberFeignService = chamberFeignService;
//        this.cashMessageGateway = cashMessageGateway;
//        this.cashMessageHTTPGateway = cashMessageHTTPGateway;
//    }

    @RequestMapping("/test")
    public String test() {
        return new Date().toString();
    }

    @RequestMapping("/getChamber") // TODO: 09.08.18 add required = false instead defaultValue
    public Chamber getChamber(@RequestParam(value = "id") Long id) {
        return chamberService.getChamberById(id);
    }

    @RequestMapping("/getChambers") // TODO: 09.08.18 add required = false instead defaultValue
    public List<Chamber> getChambersList(
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "order", defaultValue = ASC) String order,
            @RequestParam(value = "orderBy", defaultValue = ID) String orderBy,
            @RequestParam(value = "id", required = false) Integer id
    ) {

        List<Chamber> chamberList = chamberFeignService.getChambers();
//        Chamber getChamber = chamberList.get(0);
//        cashMessageGateway.send(MessageBuilder
//                .withPayload(getChamber)
//                .setHeader("CHAMBER", getChamber.getId())
//                .build()
//        );
//
//        // TODO: 20.08.18 дебажить *5
//        cashMessageHTTPGateway.send(MessageBuilder
//                .withPayload(getChamber)
//                .setHeader("CHAMBER_HTTP", getChamber.getId())
//                .build()
//        );

        return chamberList;
    }
}
