package com.B2B.SP.user.mapper;

import com.B2B.SP.user.dto.UserDto;
import com.B2B.SP.user.model.User;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-01T11:02:15+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.3.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto userToDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setUserId( user.getUserId() );
        userDto.setUserName( user.getUserName() );
        userDto.setUserEmail( user.getUserEmail() );
        userDto.setPassword( user.getPassword() );
        userDto.setAccountType( user.getAccountType() );
        userDto.setAccountStatus( user.getAccountStatus() );

        return userDto;
    }

    @Override
    public User dtoToUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setUserId( userDto.getUserId() );
        user.setUserName( userDto.getUserName() );
        user.setPassword( userDto.getPassword() );
        user.setUserEmail( userDto.getUserEmail() );
        user.setAccountType( userDto.getAccountType() );
        user.setAccountStatus( userDto.getAccountStatus() );

        return user;
    }

    @Override
    public User dtoToUserSave(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setUserName( userDto.getUserName() );
        user.setPassword( userDto.getPassword() );
        user.setUserEmail( userDto.getUserEmail() );
        user.setAccountType( userDto.getAccountType() );
        user.setAccountStatus( userDto.getAccountStatus() );

        return user;
    }
}
