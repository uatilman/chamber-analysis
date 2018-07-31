package ru.tilman.chamberanalysis.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.tilman.chamberanalysis.entity.Region;

@Repository(value = "regionRepository")
public interface RegionRepository extends PagingAndSortingRepository<Region, Long> {


}
