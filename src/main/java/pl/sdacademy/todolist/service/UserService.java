package pl.sdacademy.todolist.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sdacademy.todolist.dto.UserDto;
import pl.sdacademy.todolist.entity.Role;
import pl.sdacademy.todolist.entity.User;
import pl.sdacademy.todolist.repository.RoleRepository;
import pl.sdacademy.todolist.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    public void create(UserDto userDto) {
        User entity = null;
        Role role = roleRepository.findByRole("USER");

        Optional<User> user = userRepository.findUserByPhoneNumber(userDto.getPhoneNumber());
        if (user.isPresent()) {
            entity = user.get();
        } else {
            entity = new User();
            entity.setRoles(Collections.singleton(role));
            entity.setPhoneNumber(userDto.getPhoneNumber());
        }

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        entity.setPassword(encodedPassword);
        entity.setEmail(userDto.getEmail());
        userRepository.save(entity);

    }

    public Optional<User> findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findUserByPhoneNumber(phoneNumber);
    }

    public User findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public User uptade(User user) {
        return userRepository.save(user);
    }

    public User createOrGetUser(String phoneNumber) {
        Role role = roleRepository.findByRole("USER");
        Optional<User> existingUser = findUserByPhoneNumber(phoneNumber);
        User user = existingUser.orElseGet(User::new);
        user.setPhoneNumber(phoneNumber);
        user.setRoles(Collections.singleton(role));
        return user;
    }
}