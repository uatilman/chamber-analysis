package repository;

import entity.security.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository(value = "roleRepository")
public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
    Role findByName(String name);
}
