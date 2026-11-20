package com.company.booking.service;

import com.company.booking.model.dto.CreateUserRq;
import com.company.booking.model.dto.FullUserRs;
import com.company.booking.model.dto.UserRs;
import com.company.booking.model.entity.User;
import com.company.booking.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserRs create (CreateUserRq dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User(dto.name(), dto.email());

        User savedUser = userRepository.save(user);
        return new UserRs(savedUser.getId(), savedUser.getName());
    }

    public FullUserRs getUser (Long id, String email) {
        if (userRepository.existsByEmail(email)) {
            User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found!"));
            return new FullUserRs(user.getId(), user.getName(), email, user.getRole().toString(), user.getCreatedAt());
        }

        if (userRepository.existsById(id)) {
            User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found!"));
            return new FullUserRs(id, user.getName(), user.getEmail(), user.getRole().toString(), user.getCreatedAt());
        }

        throw new RuntimeException("User not found!");
    }
}