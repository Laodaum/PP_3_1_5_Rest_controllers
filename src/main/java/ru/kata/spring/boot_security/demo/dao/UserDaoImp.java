package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImp implements UserDao {

   @PersistenceContext
   private EntityManager entityManager;

   @Override
   public void add(User user) {
      entityManager.persist(user);
   }

   @Override
   @SuppressWarnings(value = "unchecked")
   public List<User> listUsers() {
      return entityManager.createQuery( "from User" ).getResultList();
   }

   @Override
   public void removeUserById(Long id) {
      entityManager.createQuery("DELETE User WHERE id = :id").setParameter("id",id).executeUpdate();
      }
   @Override
   public User findUser(Long id) {
      return entityManager.find(User.class, id );
   }
   @Override
   public void update(User user) {
      entityManager.merge(user);
   }

   @Override
   public User findUserByLogin(String login) throws NoResultException {
      try {
         return (User)entityManager.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.login = :login").setParameter("login", login).getSingleResult();
      } catch (NoResultException noResultException) {
         System.out.println("User not found!");
         throw new UsernameNotFoundException("User not found!",noResultException);
      }
   }
}
