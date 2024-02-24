package org.example.controller;

import org.example.dto.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class ControllerUser {
    public ControllerUser(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

    @PostMapping
    public User add(@RequestBody User user){
        return userService.add(user);

    }

}
