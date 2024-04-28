package com.example.demo.common.dto;

import com.example.demo.constraints.ValidAge;
import com.example.demo.constraints.ValidEmail;

import java.time.LocalDateTime;

public record UserDTO(String uuid, @ValidEmail String email, String firstName, String lastName, @ValidAge LocalDateTime birthDate) {
}
