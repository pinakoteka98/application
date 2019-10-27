package pl.sdacademy.todolist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sdacademy.todolist.entity.User;
import pl.sdacademy.todolist.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ApplicationUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User entity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));

        String[] roles = {"USER", "ADMIN"};
        return org.springframework.security.core.userdetails.User
                .withUsername(entity.getUsername()).password(entity.getPassword()).roles(roles)
                .build();
    }
}
