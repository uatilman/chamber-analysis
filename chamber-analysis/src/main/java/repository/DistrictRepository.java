package repository;

import entity.District;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "districtRepository")
public interface DistrictRepository extends PagingAndSortingRepository<District, Long> {


}
