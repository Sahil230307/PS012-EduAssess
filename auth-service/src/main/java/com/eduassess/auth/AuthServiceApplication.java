package com.eduassess.auth;

import com.eduassess.auth.entity.Role;
import com.eduassess.auth.entity.User;
import com.eduassess.auth.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner seedUsers(UserRepository repository, PasswordEncoder encoder) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new User(null, "Admin User", "admin@eduassess.com",
                        encoder.encode("Admin@123"), Role.ADMIN));
                repository.save(new User(null, "Demo Student", "student@eduassess.com",
                        encoder.encode("Student@123"), Role.STUDENT));
            }
        };
    }
}
