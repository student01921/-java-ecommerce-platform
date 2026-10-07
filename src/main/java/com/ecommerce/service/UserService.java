package com.ecommerce.service;

import com.ecommerce.dto.UserRegistrationDto;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public User registerUser(UserRegistrationDto dto) {
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .passwordHash(passwordEncoder.encode(dto.getPassword()))
                .role(Role.valueOf(dto.getRole()))
                .phone(dto.getPhone())
                .build();
        return userRepository.save(user);
    }
}
