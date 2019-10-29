package pl.sdacademy.todolist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.sdacademy.todolist.entity.Role;
import pl.sdacademy.todolist.entity.User;
import pl.sdacademy.todolist.repository.UserRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User entity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
        String[] roles = entity.getRoles().stream().map(Role::getRole).toArray(String[]::new);
        return org.springframework.security.core.userdetails.User
                .withUsername(entity.getUsername()).password(entity.getPassword()).roles(roles)
                .build();
    }
}
