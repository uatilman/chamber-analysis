package ru.tilman.chamberanalysis.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.tilman.chamberanalysis.entity.security.Role;

@Repository(value = "roleRepository")
public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
    Role findByName(String name);
}
