package com.B2B.SP.user.mapper;

import com.B2B.SP.user.dto.UserDto;
import com.B2B.SP.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = UUID.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToDto(User user);

    User dtoToUser(UserDto userDto);

    @Mapping(target = "userId", ignore = true)
    User dtoToUserSave(UserDto userDto);
}
