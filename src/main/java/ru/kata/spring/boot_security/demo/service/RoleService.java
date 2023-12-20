package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Set;

public interface RoleService {
    Role findRole(String role);
    Set<Role> rolesSet();
    void add(Role role);

}
