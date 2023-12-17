package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static ru.kata.spring.boot_security.demo.configs.WebSecurityConfig.passwordEncoder;

@Service
public class UserServiceImp implements UserService {


   private UserDao userDao;

   private PasswordEncoder passwordEncoder;


   @Autowired
   public UserServiceImp(UserDao userDao, PasswordEncoder passwordEncoder) {
      this.userDao = userDao;
      this.passwordEncoder= passwordEncoder;
   }

   @Transactional
   @Override
   public void add(User user) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      userDao.add(user);
   }

   @Transactional(readOnly = true)
   @Override
   public List<User> listUsers() {
      return userDao.listUsers();

   }
   @Transactional
   @Override
   public void removeUserById(Long id) {
      userDao.removeUserById(id);
   }

   @Override
   public User findUser(Long id) {
     return userDao.findUser(id);
   }
   @Transactional
   @Override
   public void update(User changedUser) {
      userDao.update(changedUser);
   }


}
