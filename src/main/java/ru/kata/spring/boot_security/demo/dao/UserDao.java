package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {


   void add(User user);
   List<User> listUsers();
   void removeUserById(Long id);
   User findUser(Long id);
   void update(User user);
   User findUserByLogin(String login);

}
