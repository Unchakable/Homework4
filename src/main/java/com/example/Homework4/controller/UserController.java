package com.example.Homework4.controller;

import com.example.Homework4.model.dto.UserRequestDTO;
import com.example.Homework4.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRequestDTO userDTO) {
        try {
            return userService.createUser(userDTO);
        } catch (Exception e) {
            log.error("user creation error, name = {}", userDTO.getUserName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsersById(@PathVariable int id) {
        log.info("Get /api/users/{}", id);
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (Exception e) {
            log.error("Error getting the user by id /api/users/{}", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        log.info("GET all users");
        try {
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (Exception e) {
            log.error("Error getting all users");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody @Valid UserRequestDTO userDTO) {
        log.info("PUT /api/users/{}", id);
        try {
            return userService.updateUser(id, userDTO);
        } catch (Exception e){
            log.error("Error updating user {}", id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserByID(@PathVariable int id) {
        log.info("DELETE /api/users/{}", id);
        try {
            return userService.deleteUserById(id);
        }catch (Exception e){
            log.error("Error deleting a user by id {}", id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllUsers() {
        log.info("DELETE all users");
        try {
            return userService.deleteAllUsers();
        } catch (Exception e) {
            log.error("Error deleting all users");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}
