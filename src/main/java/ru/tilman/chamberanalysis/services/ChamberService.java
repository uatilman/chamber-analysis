package ru.tilman.chamberanalysis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.tilman.chamberanalysis.entity.Chamber;
import ru.tilman.chamberanalysis.repository.ChamberRepository;

import java.util.Collections;
import java.util.List;

@Service
public class ChamberService {

    private final ChamberRepository chamberRepository;

    @Autowired
    public ChamberService(@Qualifier("chamberRepository") ChamberRepository chamberRepository) {
        this.chamberRepository = chamberRepository;
    }

    public List<Chamber> getChambersListByOrderByIdAsc() {
        return chamberRepository.findAllByOrderByIdAsc();
    }

    public Page<Chamber> getChambersPage(PageRequest pageable) {
        return chamberRepository.findAll(pageable);
    }

    public Chamber getChamberById(Long id) {
        if (chamberRepository.findById(id).isPresent())
            return chamberRepository.findById(id).get();
        return null;
    }

    public Chamber getChamberById(String id) {
        return getChamberById(Long.valueOf(id));
    }

    public List<Chamber> getChamberListById(Integer id) {
        return Collections.singletonList(getChamberById(Long.valueOf(id)));
    }

    public Integer getChambersSize() {
        return Math.toIntExact(chamberRepository.count());
    }


}
