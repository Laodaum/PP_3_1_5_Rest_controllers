package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
     public String newUser(@ModelAttribute("user") User user) {
        return "/admin/addForm";
    }
    @PostMapping()
    public String addUser(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/admin";
    }
    @GetMapping("/admin/change")
    public String changeUser(@RequestParam(name = "id") Long id, Model model) {
        model.addAttribute("user", userService.findUser(id));
        return "/admin/changeForm";
    }
    @PostMapping("/admin/change")
    public String update( @ModelAttribute("user") User changedUser) {
        userService.update(changedUser);
        return "redirect:/admin";
    }

}
