package com.example.demo.service;

import com.example.demo.common.dto.UserDTO;
import com.example.demo.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;

    @Mock
    private Map<String, User> userMap;

    @BeforeEach
    public void init() {
        userService = new UserService(userMap);
    }

    @Test
    void test_saveUser() {
        UserDTO resultUser = new UserDTO("2", "anton@gmail.com", "Anton", "ann",
                LocalDateTime.of(2004, 12, 13, 12, 30));

        User user = new User(resultUser.uuid(), resultUser.email(), resultUser.firstName(), resultUser.lastName(), resultUser.birthDate());

        when(userMap.get("2")).thenReturn(user);

        UserDTO userDTO = userService.saveUser(resultUser);

        Assertions.assertEquals(resultUser, userDTO);
    }

    @Test
    void test_getAllUsers() {
        LocalDateTime from = LocalDateTime.of(2000, 1, 1, 0, 0);
        LocalDateTime to = LocalDateTime.of(2010, 1, 1, 0, 0);

        Map<String, User> map = new HashMap<>();
        map.put("1", new User("1", "anton@gmail.com", "Anton", "ann",
                LocalDateTime.of(2004, 12, 13, 12, 30)));

        when(userMap.values()).thenReturn(map.values());

        List<UserDTO> users = userService.getAllUsers(from, to);

        Assertions.assertEquals("1", users.get(0).uuid());
    }

    @Test
    void test_deleteUser() {
        Map<String, User> map = new HashMap<>();
        map.put("1", new User("1", "anton@gmail.com", "Anton", "ann",
                LocalDateTime.of(2004, 12, 13, 12, 30)));

        when(userMap.remove("1")).thenReturn(map.remove("1"));

        userService.deleteUser("1");

        Assertions.assertEquals(0, map.size());
    }

    @Test
    void test_changeAllUserFields() {
        User user = new User("1", "anton@gmail.com", "Anton", "ann1",
                LocalDateTime.of(2005, 12, 13, 12, 30));
        Map<String, User> map = new HashMap<>();
        map.put("1", user);

        when(userMap.get("1")).thenReturn(user);

        UserDTO userDTO = userService.changeAllUserFields("1", new UserDTO("1", "anton1@gmail.com", "Anton1", "ann1",
                LocalDateTime.of(2005, 12, 13, 12, 30)));

        Assertions.assertEquals("anton1@gmail.com", userDTO.email());
    }
}