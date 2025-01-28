package com.chat.demo.users;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/")
    public String in() {
        return "welcome";
    }
    // @GetMapping("/login")
    // public String login() {
    //     return "<!DOCTYPE html>"
    //             + "<html>"
    //             + "<head><title>Login</title></head>"
    //             + "<body>"
    //             + "<h1>Login</h1>"
    //             + "<form method='post' action='/login'>"
    //             + "<div><label>Username:</label><input type='text' name='username'/></div>"
    //             + "<div><label>Password:</label><input type='password' name='password'/></div>"
    //             + "<div><button type='submit'>Login</button></div>"
    //             + "</form>"
    //             + "</body>"
    //             + "</html>"; 
    // }

    @GetMapping("/index")
    public String index() {
        return "welcome";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }
    
    @GetMapping("/userinfo")
    public User userinfo() {
        return userService.userinfo();
    }

    @PostMapping("/save-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> saveUser(@RequestBody User user) {
        System.out.println(user);
        return userService.saveUser(user);
 }
}
