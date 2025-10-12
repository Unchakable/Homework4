package com.example.Homework4.controller;

import com.example.Homework4.AbstractIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AbstractIntegrationTest {

    @Test
    @Sql(scripts = "classpath:sql/users.sql")
    @Sql(scripts = "classpath:sql/clean.sql", executionPhase = AFTER_TEST_METHOD)
    void shouldReturnUserByID() throws Exception {
        //Then
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.userName").value("Alex"))
                .andExpect(jsonPath("$.email").value("123456789@mail.ru"))
                .andExpect(jsonPath("$.age").value(20));
    }

    @Test
    void shouldReturnBadRequestIfUserNotFound() throws Exception {
        //then
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(scripts = "classpath:sql/users.sql")
    @Sql(scripts = "classpath:sql/clean.sql", executionPhase = AFTER_TEST_METHOD)
    void shouldReturnAllUsers() throws Exception {
        //Then
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
        ;

    }

    @Test
    @Sql(scripts = "classpath:sql/users.sql")
    @Sql(scripts = "classpath:sql/clean.sql", executionPhase = AFTER_TEST_METHOD)
    void shouldUpdateUsers() throws Exception {
        //Given
        String userJson = """
                {
                    "userName": "Alex",
                    "email": "987654321@mail.ru",
                    "age": 30
                }
                """;
        //Then
        mockMvc.perform(put("/api/users/1")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(userJson))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.userName").value("Alex"))
                .andExpect(jsonPath("$.email").value("987654321@mail.ru"))
                .andExpect(jsonPath("$.age").value(30));

    }

    @Test
    @Sql(scripts = "classpath:sql/users.sql")
    @Sql(scripts = "classpath:sql/clean.sql", executionPhase = AFTER_TEST_METHOD)
    void shouldDeleteAllUser() throws Exception {
        mockMvc.perform(delete("/api/users"))
                .andExpect(status().isOk());

        Assertions.assertTrue(userRepository.findAll().isEmpty());
    }
}
