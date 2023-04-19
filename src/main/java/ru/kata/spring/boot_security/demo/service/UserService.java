package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> listUsers();
    void saveUser(User user);
    User getUser(Long id);
    void deleteUser(Long id);
    User findUserById(Long id);
    User findByUsername (String firstName);
    User findByEmail(String Email);
    void update (Long id, User user);
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
}
