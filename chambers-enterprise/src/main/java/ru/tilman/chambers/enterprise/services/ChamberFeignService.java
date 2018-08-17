package ru.tilman.chambers.enterprise.services;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.tilman.chambers.enterprise.entity.Chamber;

import java.util.List;

@FeignClient("chambers-microservice")
public interface ChamberFeignService {

    @RequestMapping("rest/getChambers")
// TODO: 17.08.18  rename, crud
    List<Chamber> getChambers();

}
