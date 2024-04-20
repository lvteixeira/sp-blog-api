package com.acme.blogApi.controller;

import com.acme.blogApi.dto.UserDTO;
import com.acme.blogApi.model.UserEntity;
import com.acme.blogApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{username}/auth")
    public ResponseEntity<UserDTO> authenticate(@PathVariable("username") String username, @RequestParam String password) {
        return userService.authenticate(username, password)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO createPayload) {
        UserDTO createdUser = userService.create(createPayload);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
