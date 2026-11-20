package com.company.booking.controller;

import com.company.booking.model.dto.CreateUserRq;
import com.company.booking.model.dto.FullUserRs;
import com.company.booking.model.dto.UpdateUser;
import com.company.booking.model.dto.UserRs;
import com.company.booking.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v0/users")
public class UserApiController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserRs> addUser(@RequestBody @Valid CreateUserRq userRq) {
        UserRs user = userService.create(userRq);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping({"/{id}", "/{email}"})
    public ResponseEntity<FullUserRs> getUser(@PathVariable Long id, @PathVariable String email) {
        FullUserRs user = userService.getUser(id, email);
        return ResponseEntity.ok(user);
    }

    @PatchMapping({"/{id}", "/{email}"})
    public ResponseEntity<UserRs> updateUser (@PathVariable Long id, @PathVariable String email, @RequestBody UpdateUser updateUser) {
        UserRs user = userService.updateUser(id, email, updateUser);
        return ResponseEntity.ok(user);
    }
}