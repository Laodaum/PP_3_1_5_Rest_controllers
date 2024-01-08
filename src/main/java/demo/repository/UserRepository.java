package demo.repository;

import demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.id = :id")
    Optional<User> findById(Long id);

    @Query("SELECT COUNT(*) FROM User")
    Long countUsers();

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.login = :login")
    Optional<User> findByLogin(String login);
}
