package ru.tilman.chamberanalysis.controllers;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tilman.chamberanalysis.entity.Chamber;
import ru.tilman.chamberanalysis.repository.ChamberRepository;

import java.util.List;

@RestController
public class RestChambersController {

    private final ChamberRepository chamberRepository;

    @Autowired
    public RestChambersController(@Qualifier("chamberRepository") ChamberRepository chamberRepository) {
        this.chamberRepository = chamberRepository;
    }

    @RequestMapping("/getChambers")
    public ChamberAjax getChambersList(
            @RequestParam("pageCounter") Integer pageCounter,
            @RequestParam("number") Integer number,
            @RequestParam("order") String order,
            @RequestParam("orderBy") String orderBy
    ) {

        Sort sort = null;

        if (order.equalsIgnoreCase("DESC")) {
            sort = new Sort(Sort.Direction.DESC, orderBy);
        } else {
            sort = new Sort(Sort.Direction.ASC, orderBy);
        }

        PageRequest pageable = PageRequest.of(pageCounter, number, sort);
        Page<Chamber> chamberPage = chamberRepository.findAll(pageable);


        ChamberAjax responsive = new ChamberAjax();
        responsive.setChambers(Lists.newArrayList(chamberPage.iterator()));
        return responsive;
    }
}
