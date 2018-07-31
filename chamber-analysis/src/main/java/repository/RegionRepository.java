package repository;

import entity.Region;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "regionRepository")
public interface RegionRepository extends PagingAndSortingRepository<Region, Long> {


}
