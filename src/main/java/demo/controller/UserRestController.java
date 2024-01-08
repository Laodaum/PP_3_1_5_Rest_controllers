package demo.controller;

import demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import demo.models.Role;
import demo.service.RoleService;
import demo.service.UserService;
import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.List;


@CrossOrigin
@RestController
public class UserRestController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public UserRestController(UserService userService,RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") Long id) {
        return userService.findById(id).orElseThrow();
    }

    @GetMapping("/users/roles")
    public List<Role> getAllRoles() {
        return roleService.findAll();
    }

    @PutMapping("/usersEdit")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<?> addNewUser(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<User> getCurrentUser(Principal principal){
        System.out.println(principal);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PostConstruct
    public void init(){
        userService.addFirstAdmin();
    }






}
