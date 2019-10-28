package pl.sdacademy.todolist.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import pl.sdacademy.todolist.entity.Role;
import pl.sdacademy.todolist.repository.RoleRepository;

@RequiredArgsConstructor
@Configuration
public class LaunchConfig {

    private final RoleRepository roleRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void loadRoles() {

        Role roleAdmin = roleRepository.findByRole("ROLE_ADMIN");
        Role roleUser = roleRepository.findByRole("ROLE_USER");

        if (roleAdmin == null) {
            roleAdmin = new Role(1L, "ROLE_ADMIN");
            roleRepository.save(roleAdmin);
        }
        if (roleUser == null) {
            roleUser = new Role(2L, "ROLE_USER");
            roleRepository.save(roleUser);
        }
    }
}
