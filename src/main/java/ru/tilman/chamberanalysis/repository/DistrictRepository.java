package ru.tilman.chamberanalysis.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.tilman.chamberanalysis.entity.District;

@Repository(value = "districtRepository")
public interface DistrictRepository extends PagingAndSortingRepository<District, Long> {


}
