package com.acme.blogApi.controller;

import com.acme.blogApi.dto.UserDTO;
import com.acme.blogApi.model.UserEntity;
import com.acme.blogApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
