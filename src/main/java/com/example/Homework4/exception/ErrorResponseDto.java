package com.example.Homework4.exception;
import java.util.Map;

public record ErrorResponseDto(
    Integer status,
    String message,
    Map<String, Object> details) {
}