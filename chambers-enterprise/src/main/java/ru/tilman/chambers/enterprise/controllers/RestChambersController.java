package ru.tilman.chambers.enterprise.controllers;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tilman.chambers.enterprise.entity.Chamber;
import ru.tilman.chambers.enterprise.services.ChamberService;

import java.util.List;

@RestController
@RequestMapping("rest")
public class RestChambersController {

    public static final String DESC = "DESC";
    public static final String ASC = "ASC";
    public static final String ID = "id";

    private final ChamberService chamberService;

    @Autowired
    public RestChambersController(@Qualifier("chamberService") ChamberService chamberService) {
        this.chamberService = chamberService;
    }

    @RequestMapping("/getChambers") // TODO: 09.08.18 add required = false instead defaultValue
    public List<Chamber> getChambersList(
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "order", defaultValue = ASC) String order,
            @RequestParam(value = "orderBy", defaultValue = ID) String orderBy,
            @RequestParam(value = "id", required = false) Integer id
    ) {

        if (id != null) return chamberService.getChamberListById(id);
        if (size == null) size = chamberService.getChambersSize();

        Sort sort = null;
        if (order.equalsIgnoreCase(DESC)) sort = new Sort(Sort.Direction.DESC, orderBy);
        else sort = new Sort(Sort.Direction.ASC, orderBy);

        PageRequest pageable = PageRequest.of(pageNumber, size, sort);
        Page<Chamber> chamberPage = chamberService.getChambersPage(pageable);

        return Lists.newArrayList(chamberPage.iterator());
    }

}
