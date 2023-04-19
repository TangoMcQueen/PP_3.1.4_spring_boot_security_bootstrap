package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Init {
    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public Init(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void initializeDB() {
        roleService.save(new Role("ROLE_ADMIN"));
        roleService.save(new Role("ROLE_USER"));
        Set<Role> adminRole = new HashSet<>();
        Set<Role> userRole = new HashSet<>();
        Set<Role> allRoles = new HashSet<>();
        adminRole.add(roleService.findById(1L));
        userRole.add(roleService.findById(2L));
        allRoles.add(roleService.findById(1L));
        allRoles.add(roleService.findById(2L));
        userService.saveUser(new User("Admin", "Adminov", 50, "Admin@xyz.ru", "admin", adminRole));
        userService.saveUser(new User("User", "Userov", 35, "User@xyz.ru", "user", userRole));
        userService.saveUser(new User("Tester", "VseRolev", 30, "Test@xyz.ru", "test", allRoles));
    }
}
