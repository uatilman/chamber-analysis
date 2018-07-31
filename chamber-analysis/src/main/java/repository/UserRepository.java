package repository;

import entity.security.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository(value = "userRepository")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByLogin(String login);
}
