package pl.sdacademy.todolist.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


@Slf4j
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/register").permitAll()
                .antMatchers("/scheduleunlogged", "/confirmationunlogged", "/", "/index").anonymous()
                .antMatchers("/menu", "/services/all").hasAnyRole("USER", "ADMIN")
                .antMatchers("/addorder", "/orders", "/edit", "/services/all/**").hasRole("ADMIN")
                .antMatchers("/list/**", "/schedule", "/confirmation", "serviceaskconfirmation").hasRole("USER")
                .antMatchers("/**/*.js", "/**/*.css").permitAll()
                .antMatchers("/resources/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/menu")
                .usernameParameter("login")
                .passwordParameter("password")
                .and()
                .logout().logoutSuccessUrl("/login")
                .and()
                .httpBasic()
                .and()
                .csrf().disable();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}