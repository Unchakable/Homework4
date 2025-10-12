package com.example.Homework4.controller;

import com.example.Homework4.model.dto.UserResponseDTO;
import com.example.Homework4.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.matchesPattern;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    private UserResponseDTO getTestUserResponseDTO(){
        return new UserResponseDTO(1, "Alex", "123456789@mail.ru", 20, LocalDateTime.now());
    }

    @Test
    void shouldReturnUserByID() throws Exception {
        //Given
        UserResponseDTO userResponseDTO = getTestUserResponseDTO();
        Mockito.when(this.userService.getUserById(1)).thenReturn(userResponseDTO);

        //When
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userResponseDTO.getId()))
                .andExpect(jsonPath("$.userName").value(userResponseDTO.getUserName()))
                .andExpect(jsonPath("$.email").value(userResponseDTO.getEmail()))
                .andExpect(jsonPath("$.createdAt").isString())
                .andExpect(jsonPath("$.createdAt")
                        .value(matchesPattern("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d+Z?$")));

        //Then
        verify(userService).getUserById(1);
    }

    @Test
    void shouldReturnAllUsers() throws Exception {
        //Given
        UserResponseDTO userResponseDTO = getTestUserResponseDTO();
        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();
        userResponseDTOList.add(userResponseDTO);
        Mockito.when(this.userService.getAllUsers()).thenReturn(userResponseDTOList);

        //When
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));;

        //Then
        verify(userService).getAllUsers();
    }

}
