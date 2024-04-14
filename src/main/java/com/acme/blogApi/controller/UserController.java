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
    public ResponseEntity<UserDTO> authenticate(@PathVariable String username, @RequestParam String password) {
        try {
            var user = userService.authenticate(username, password);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO createPayload) throws Exception {
        UserEntity obj = userService.convertDTOToEntity(createPayload);
        userService.create(obj);
        UserDTO createdUser = userService.convertEntityToDTO(obj);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdUser);
    }
}
