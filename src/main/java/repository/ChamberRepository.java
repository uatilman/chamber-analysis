package repository;

import entity.Chamber;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "chamberRepository")
public interface ChamberRepository extends PagingAndSortingRepository<Chamber, Long> {
    List<Chamber> findByNameLikeOrderByIdAsc(String name);

    List<Chamber> findAllByOrderByIdAsc();

}
