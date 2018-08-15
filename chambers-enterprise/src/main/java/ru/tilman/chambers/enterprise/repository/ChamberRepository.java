package ru.tilman.chambers.enterprise.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.tilman.chambers.enterprise.entity.Chamber;

import java.util.List;

@Repository(value = "chamberRepository")
public interface ChamberRepository extends PagingAndSortingRepository<Chamber, Long> {
    List<Chamber> findByNameLikeOrderByIdAsc(String name);

    List<Chamber> findAllByOrderByIdAsc();

}
