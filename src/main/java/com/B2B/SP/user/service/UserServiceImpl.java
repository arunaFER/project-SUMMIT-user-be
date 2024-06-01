package com.B2B.SP.user.service;

import com.B2B.SP.user.dto.UserDto;
import com.B2B.SP.user.exceptions.customexceptions.BadRequestException;
import com.B2B.SP.user.exceptions.customexceptions.UserNotFoundException;
import com.B2B.SP.user.mapper.UserMapper;
import com.B2B.SP.user.model.User;
import com.B2B.SP.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final EntityManager entityManager;

    // Constructor
    public UserServiceImpl(UserRepository userRepository, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        try {
            logger.info("Finding all users");

            return userRepository.findAllByAccountNotInActive()
                    .stream()
                    .map(UserMapper.INSTANCE::userToDto)
                    .collect(Collectors.toList());

        }catch (Exception e){
            logger.error("Exception while finding all users", e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findById(Long userId){
        try{
            logger.info("Finding user by id: {}", userId);

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

            return UserMapper.INSTANCE.userToDto(user);
        }catch (Exception e){
            logger.error("Exception while finding user by id: {}", userId, e);
            throw e;
        }
    }

    @Override
    public UserDto findByUsername(String username) {
        try{
            logger.info("Finding user by username: {}", username);

            User user = userRepository.findByUserNameAccountNotInActive(username)
                    .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));

            return UserMapper.INSTANCE.userToDto(user);
        }catch (Exception e){
            logger.error("Exception while finding user by username: {}", username, e);
            throw e;
        }
//        return null;
    }

    @Override
    @Transactional
    public UserDto save(UserDto userDto) {
        try{
            if (Objects.nonNull(userDto.getUserId())) {
                throw new BadRequestException("Saving user does not need an ID");
            }

            if(userDto.getAccountStatus() == User.AccountStatus.INACTIVE){
                throw new BadRequestException("User account status cannot be INACTIVE");
            }

            logger.info("Saving user: {}", userDto);
            User user = UserMapper.INSTANCE.dtoToUserSave(userDto);
            User savedUser = userRepository.save(user);

            return UserMapper.INSTANCE.userToDto(savedUser);
        }catch (Exception e){
            logger.error("Exception while saving user", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public UserDto update(UserDto userDto) {
        Long userId = userDto.getUserId();

        if (userId == null){
            logger.error("Cannot update user without id");
            throw new BadRequestException("Cannot update user without id");
        }

        if (userDto.getAccountStatus() == User.AccountStatus.INACTIVE){
            throw new BadRequestException("Cannot update user account status as INACTIVE");
        }

        logger.info("Updating user: {}", userDto);

        Optional<User> optionalUser = userRepository.findByIdAccountNotInActive(userId);

        if (optionalUser.isEmpty()){
            logger.error("Cannot update deleted user");
            throw new BadRequestException("Cannot update deleted user");
        }

        User updatedUser = UserMapper.INSTANCE.dtoToUser(userDto);
        updatedUser = userRepository.save(updatedUser);
        return UserMapper.INSTANCE.userToDto(updatedUser);
    }

    @Override
    public void deleteById(Long userId) {
        try{
            logger.info("Deleting user: {}", userId);

            Optional<User> optionalUser = userRepository.findByIdAccountNotInActive(userId);

            if (optionalUser.isEmpty()){
                logger.error("User already deleted");
                throw new UserNotFoundException("User already deleted");
            }

            userRepository.deleteById(userId);
        }catch (Exception e){
            logger.error("Exception while deleting user", e);
            throw e;
        }
    }
}
