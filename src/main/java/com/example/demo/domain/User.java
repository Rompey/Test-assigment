package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class User {

    private String uuid;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime birthDate;
}
