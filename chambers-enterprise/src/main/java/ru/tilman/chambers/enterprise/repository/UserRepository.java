package ru.tilman.chambers.enterprise.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.tilman.chambers.enterprise.entity.security.User;

@Repository(value = "userRepository")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByLogin(String login);
}
