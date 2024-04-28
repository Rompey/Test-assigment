package com.example.demo.controller;

import com.example.demo.common.dto.UserDTO;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private final String userUrl = "/api/v1/users";

    @Test
    void test_saveUser() throws Exception {
        UserDTO userDTO = new UserDTO("1", "anton@gmail.com", "Anton", "ann",
                LocalDateTime.of(2004, 12, 13, 12, 30));

        when(userService.saveUser(userDTO)).thenReturn(userDTO);

        mockMvc.perform(post(userUrl)
                        .param("email", "anton@gmail.com")
                        .param("firstName", "Anton")
                        .param("lastName", "ann")
                        .param("birthDate", "2004-12-13T12:30:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpectAll();
    }

    @Test
    void test_deleteUser() throws Exception {
        String uuid = "1";

        doNothing().when(userService).deleteUser(uuid);

        mockMvc.perform(delete(userUrl)
                        .param("uuid", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpectAll();
    }

    @Test
    void test_changeAllUserFields() throws Exception {
        UserDTO userDTO = new UserDTO("1", "anton1@gmail.com", "Anton1", "ann1",
                LocalDateTime.of(2005, 12, 13, 12, 30));
        String uuid = "1";

        when(userService.changeAllUserFields(uuid, userDTO)).thenReturn(userDTO);

        mockMvc.perform(put(userUrl)
                        .param("uuid", "1")
                        .param("email", "anton1@gmail.com")
                        .param("firstName", "Anton1")
                        .param("lastName", "ann1")
                        .param("birthDate", "2005-12-13T12:30:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpectAll();
    }

    @Test
    void getAllUsers() throws Exception {
        List<UserDTO> users = List.of(new UserDTO("1", "anton1@gmail.com", "Anton1", "ann1",
                LocalDateTime.of(2005, 12, 13, 12, 30)));

        LocalDateTime from = LocalDateTime.of(2001, 1, 1, 0, 0);
        LocalDateTime to = LocalDateTime.of(2007, 1, 1, 0, 0);

        when(userService.getAllUsers(from, to)).thenReturn(users);

        mockMvc.perform(get(userUrl)
                        .param("from", "2001-01-01T00:00:00")
                        .param("to", "2007-01-01T00:00:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpectAll();
    }
}