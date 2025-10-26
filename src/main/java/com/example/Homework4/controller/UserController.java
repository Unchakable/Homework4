package com.example.Homework4.controller;

import com.example.Homework4.model.dto.UserRequestDTO;
import com.example.Homework4.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRequestDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsersById(@PathVariable int id) {
        log.info("Get /api/users/{}", id);
        return ResponseEntity.ok(userService.getUserById(id));
    }


    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        log.info("GET all users");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody @Valid UserRequestDTO userDTO) {
        log.info("PUT /api/users/{}", id);
        return userService.updateUser(id, userDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserByID(@PathVariable int id) {
        log.info("DELETE /api/users/{}", id);
        return userService.deleteUserById(id);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllUsers() {
        log.info("DELETE all users");
        return userService.deleteAllUsers();
    }
}
