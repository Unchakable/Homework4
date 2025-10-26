package com.example.Homework4.mapper;

import com.example.Homework4.model.dto.UserRequestDTO;
import com.example.Homework4.model.dto.UserResponseDTO;
import com.example.Homework4.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "name", source = "userName")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User toUser(UserRequestDTO userRequestDTO);

    @Mapping(target = "userName", source = "name")
    UserResponseDTO toResponseDTO(User user);

    @Mapping(target = "name", source = "userName")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User updateEntityFromDTO(UserRequestDTO userRequestDTO, @MappingTarget User user);

    List<UserResponseDTO> toResponseDTOList(List<User> users);
}
