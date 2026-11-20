package com.company.booking.service;

import com.company.booking.model.dto.CreateUserRq;
import com.company.booking.model.dto.FullUserRs;
import com.company.booking.model.dto.UpdateUser;
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

    @Transactional
    public UserRs updateUser (Long id, String email, UpdateUser dto) {
        String updateName = normalize(dto.getName());
        String updateEmail = normalize(dto.getEmail());

        User user = findUser(id, email);

        if (updateName != null) {
            user.setName(updateName);
        }

        if (updateEmail != null && !updateEmail.equals(user.getEmail())) {
            if (userRepository.existsByEmail(updateEmail)) {
                throw new RuntimeException("Email already exists");
            }

            user.setEmail(updateEmail);
        }

        User savedUser = userRepository.save(user);
        return new UserRs(savedUser.getId(), savedUser.getName());
    }

    private User findUser (Long id, String email) {
        if (id != null) {
            return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found!"));
        }

        if (email != null) {
            return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found!"));
        }

        throw new  RuntimeException("Name and/or Email are required!");
    }

    private String normalize (String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }
}