package demo.service;

import demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void save(User user);
    List<User> findAll();
    void deleteById(Long id);
    Optional<User> findById(Long id);
    void update(User updatedUser);
    void addFirstAdmin();


}
