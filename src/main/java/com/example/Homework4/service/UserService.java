package com.example.Homework4.service;

import com.example.Homework4.exception.NotFoundException;
import com.example.Homework4.mapper.UserMapper;
import com.example.Homework4.model.dto.UserRequestDTO;
import com.example.Homework4.model.dto.UserResponseDTO;
import com.example.Homework4.model.entity.User;
import com.example.Homework4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public ResponseEntity<Void> createUser(UserRequestDTO userCreateDTO) {
        User user = userMapper.toUser(userCreateDTO);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public UserResponseDTO getUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found user id = ".concat(String.valueOf(id))));
        return userMapper.toResponseDTO(user);
    }

    public List<UserResponseDTO> getAllUsers() {
        return userMapper.toResponseDTOList(userRepository.findAll());

    }

    public ResponseEntity<Void> updateUser(int id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found user id = ".concat(String.valueOf(id))));

        userMapper.updateEntityFromDTO(userRequestDTO, user);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteUserById(int id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteAllUsers() {
        userRepository.deleteAll();
        return ResponseEntity.ok().build();
    }
}
