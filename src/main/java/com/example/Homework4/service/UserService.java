package com.example.Homework4.service;

import com.example.Homework4.model.dto.UserRequestDTO;
import com.example.Homework4.model.dto.UserResponseDTO;
import com.example.Homework4.model.entity.User;
import com.example.Homework4.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    private UserResponseDTO toDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setUsername(user.getName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setAge(user.getAge());
        return userResponseDTO;
    }

    private User toUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setName(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setAge(userRequestDTO.getAge());
        return user;
    }


    public ResponseEntity<Void> createUser(UserRequestDTO userCreateDTO) {
        User user = new User();
        user.setName(userCreateDTO.getUsername());
        user.setEmail(userCreateDTO.getEmail());
        user.setAge(userCreateDTO.getAge());
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public UserResponseDTO getUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return toDTO(user);
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ResponseEntity<Void> updateUser(int id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setAge(userRequestDTO.getAge());
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteUserById(int id){
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteAllUsers(){
        userRepository.deleteAll();
        return ResponseEntity.ok().build();
    }
}
