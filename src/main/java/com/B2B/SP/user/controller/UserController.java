package com.B2B.SP.user.controller;

import com.B2B.SP.user.dto.UserDto;
import com.B2B.SP.user.model.User;
import com.B2B.SP.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> findAllUsers(){
        List<UserDto> productList = userService.findAll();
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId){
        UserDto userDto = userService.findById(userId);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username){
        UserDto userDto = userService.findByUsername(username);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/")
    public ResponseEntity<UserDto> saveUser(@Validated @RequestBody UserDto userDto){
        UserDto savedUserDto = userService.save(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDto);
    }

    @PutMapping("/")
    public ResponseEntity<UserDto> updateUser(@Validated @RequestBody UserDto userDto){
        UserDto updatedUserDto = userService.update(userDto);
        return ResponseEntity.ok(updatedUserDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
        userService.deleteById(userId);
        return ResponseEntity.ok("User deleted successfully.");
    }
}

