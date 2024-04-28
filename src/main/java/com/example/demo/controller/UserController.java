package com.example.demo.controller;

import com.example.demo.common.dto.UserDTO;
import com.example.demo.exceptions.InvalidAgeException;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDTO saveUser(@RequestBody @Valid UserDTO userDTO) {
        return userService.saveUser(userDTO);
    }

    @DeleteMapping
    public void deleteUser(@RequestParam String uuid) {
        userService.deleteUser(uuid);
    }

    @PutMapping
    public UserDTO changeAllUserFields(@RequestParam String uuid, @RequestBody @Valid UserDTO userDTO) {
        return userService.changeAllUserFields(uuid, userDTO);
    }

    @PatchMapping
    public UserDTO changeSomeUserFields(@RequestParam String uuid, @RequestBody @Valid UserDTO userDTO) {
        return userService.changeSomeUserFields(uuid, userDTO);
    }

    @GetMapping
    public List<UserDTO> getAllUsers(@RequestParam(required = false) LocalDateTime from, @RequestParam(required = false) LocalDateTime to) {
        if (from != null && to != null && (from.isAfter(to) || from.isEqual(to))) {
            throw new InvalidAgeException("Range is incorrect");
        }
        return userService.getAllUsers(from, to);
    }
}
