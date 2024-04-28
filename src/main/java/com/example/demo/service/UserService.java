package com.example.demo.service;

import com.example.demo.common.dto.UserDTO;
import com.example.demo.domain.User;
import com.example.demo.exceptions.InvalidEmailException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;


@Service
public class UserService {

    private Map<String, User> userMap;

    UserService() {

    }

    public UserService(Map<String, User> userMap) {
        this.userMap = userMap;
    }

    public UserDTO saveUser(UserDTO userDTO) {
        userMap.put(userDTO.uuid(),
                new User(userDTO.uuid(), userDTO.email(), userDTO.firstName(), userDTO.lastName(), userDTO.birthDate()));
        return mapUserDTO(userMap.get(userDTO.uuid()));
    }

    public void deleteUser(String uuid) {
        userMap.remove(uuid);
    }

    public UserDTO changeAllUserFields(String uuid, UserDTO userDTO) {
        User user = getUser(uuid);
        setUser(userDTO, user);
        userMap.put(uuid, user);
        return mapUserDTO(userMap.get(uuid));
    }

    public UserDTO changeSomeUserFields(String uuid, UserDTO userDTO) {
        User user = getUser(uuid);
        setSomeUserFields(userDTO, user);
        userMap.put(uuid, user);
        return mapUserDTO(userMap.get(uuid));
    }

    public List<UserDTO> getAllUsers(LocalDateTime from, LocalDateTime to) {
        Stream<User> users = userMap.values().stream();
        if (from != null && to != null) {
            return users.filter(user -> user.getBirthDate().isAfter(from) && user.getBirthDate().isBefore(to))
                    .map(UserService::mapUserDTO)
                    .toList();
        }
        return users.map(UserService::mapUserDTO)
                .toList();
    }

    private static UserDTO mapUserDTO(User user) {
        return new UserDTO(user.getUuid(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getBirthDate());
    }

    private User getUser(String uuid) {
        return Optional.of(userMap.get(uuid)).orElseThrow(InvalidEmailException::new);
    }

    private static void setUser(UserDTO userDTO, User user) {
        user.setEmail(userDTO.email());
        user.setFirstName(userDTO.firstName());
        user.setLastName(userDTO.lastName());
        user.setBirthDate(userDTO.birthDate());
    }

    private static void setSomeUserFields(UserDTO userDTO, User user) {
        user.setEmail(userDTO.email() != null ? userDTO.email() : user.getEmail());
        user.setFirstName(userDTO.firstName() != null ? userDTO.firstName() : user.getFirstName());
        user.setLastName(userDTO.lastName() != null ? userDTO.lastName() : user.getLastName());
        user.setBirthDate(userDTO.birthDate() != null ? userDTO.birthDate() : user.getBirthDate());
    }
}
