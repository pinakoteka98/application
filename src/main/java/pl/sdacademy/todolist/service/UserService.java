package pl.sdacademy.todolist.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sdacademy.todolist.dto.UserDto;
import pl.sdacademy.todolist.entity.User;
import pl.sdacademy.todolist.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void create (UserDto userDto){
        User entity= new User();
        entity.setFirstName(userDto.getFirstName());
        entity.setUsername(userDto.getUsername());

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);
        userRepository.save(entity);

    }
}
