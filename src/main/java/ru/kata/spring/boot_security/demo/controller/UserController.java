package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public UserController(UserService userService,RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init(){
        userService.addFirstAdmin();
    }

    @GetMapping("/")
    public String printUsers() {
        return "redirect:/login";
    }

    @GetMapping("/admin")
    public String printUsers(ModelMap model) {
        model.addAttribute( "allUsers", userService.listUsers() );
        return "admin";
    }
    @GetMapping( "/user")
    public String printUser(Authentication authentication, ModelMap model) {
        model.addAttribute( "user", (User) authentication.getPrincipal());
        return "user";
    }

    @GetMapping("/admin/delete")
    public String deleteUser(@RequestParam(name = "id") Long id, ModelMap model) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }
    @GetMapping("/admin/new")
     public String newUser(@ModelAttribute("user") User user, ModelMap modelRole) {
        modelRole.addAttribute( "modelRole", roleService.rolesSet());
        return "/admin/addForm";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user) {
        Set<Role> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            roles.add(roleService.findRole(role.getRole()));
            user.setRoles(roles);
        }
        userService.add(user);
        return "redirect:/admin";
    }
    @GetMapping("/admin/change")
    public String changeUser(@RequestParam(name = "id") Long id, ModelMap model) {
        model.addAttribute("user", userService.findUser(id));
        model.addAttribute( "modelRole", roleService.rolesSet());
        return "/admin/changeForm";
    }
    @PostMapping("/admin/change")
    public String update( @ModelAttribute("user") User changedUser) {
        Set<Role> roles = new HashSet<>();
        for (Role role : changedUser.getRoles()) {
            roles.add(roleService.findRole(role.getRole()));
            changedUser.setRoles(roles);
        }
        userService.update(changedUser);
        return "redirect:/admin";
    }

}
