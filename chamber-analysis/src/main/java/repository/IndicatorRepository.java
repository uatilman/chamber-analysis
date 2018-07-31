package repository;

import entity.Indicator;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "indicatorRepository")
public interface IndicatorRepository extends PagingAndSortingRepository<Indicator, Long> {
    List<Indicator> findAll();
}
