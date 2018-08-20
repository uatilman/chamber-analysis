package ru.tilman.chambers.enterprise.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tilman.chambers.enterprise.entity.Chamber;
import ru.tilman.chambers.enterprise.integration.CashMessageGateway;
import ru.tilman.chambers.enterprise.integration.CashMessageHTTPGateway;
import ru.tilman.chambers.enterprise.services.ChamberFeignService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("rest")
public class RestChambersController {

    public static final String DESC = "DESC";
    public static final String ASC = "ASC";
    public static final String ID = "id";

    private final ChamberFeignService chamberFeignService;
    private CashMessageGateway cashMessageGateway;
    private CashMessageHTTPGateway cashMessageHTTPGateway;

    @Autowired(required = false)
    public RestChambersController(ChamberFeignService chamberFeignService, CashMessageGateway cashMessageGateway, CashMessageHTTPGateway cashMessageHTTPGateway) {
        this.chamberFeignService = chamberFeignService;
        this.cashMessageGateway = cashMessageGateway;
        this.cashMessageHTTPGateway = cashMessageHTTPGateway;
    }

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

        List<Chamber> chamberList = chamberFeignService.getChambers();
        Chamber chamber = chamberList.get(0);
        cashMessageGateway.send(MessageBuilder
                .withPayload(chamber)
                .setHeader("CHAMBER", chamber.getId())
                .build()
        );

        // TODO: 20.08.18 дебажить *5
        cashMessageHTTPGateway.send(MessageBuilder
                .withPayload(chamber)
                .setHeader("CHAMBER_HTTP", chamber.getId())
                .build()
        );

        return chamberList;
    }
}
