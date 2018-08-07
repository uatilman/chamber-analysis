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

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("rest")
public class RestChambersController {

    private final ChamberRepository chamberRepository;


    @Autowired
    public RestChambersController(@Qualifier("chamberRepository") ChamberRepository chamberRepository) {
        this.chamberRepository = chamberRepository;
    }

    @RequestMapping("/getChambers")
    public List<Chamber> getChambersList(
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "size", defaultValue = "-1") Integer size,
            @RequestParam(value = "order", defaultValue = "ASC") String order,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(value = "id", defaultValue = "-1") Integer id
    ) {

        if (id != -1) return Collections.singletonList(chamberRepository.findById(Long.valueOf(id)).get());
        if (size == -1) size = Math.toIntExact(chamberRepository.count());

        Sort sort = null;
        // TODO: 07.08.18 "DESC" в контанту
        // TODO: 07.08.18 удалить  лишние пробелы
        if (order.equalsIgnoreCase("DESC")) sort = new Sort(Sort.Direction.DESC, orderBy);
        else sort = new Sort(Sort.Direction.ASC, orderBy);

        PageRequest pageable = PageRequest.of(pageNumber, size, sort);
        Page<Chamber> chamberPage = chamberRepository.findAll(pageable);

        return Lists.newArrayList(chamberPage.iterator());
    }

}
