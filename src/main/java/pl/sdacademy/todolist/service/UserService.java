package pl.sdacademy.todolist.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sdacademy.todolist.dto.UserDto;
import pl.sdacademy.todolist.entity.Role;
import pl.sdacademy.todolist.entity.User;
import pl.sdacademy.todolist.repository.RoleRepository;
import pl.sdacademy.todolist.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    public void create (UserDto userDto){
        User entity= new User();
        Role role = roleRepository.findByRole("USER");

        entity.setFirstName(userDto.getFirstName());
        entity.setUsername(userDto.getUsername());
        entity.setRoles(Collections.singleton(role));
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        entity.setPassword(encodedPassword);
        userRepository.save(entity);

    }
}