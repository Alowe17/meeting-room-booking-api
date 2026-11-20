package com.company.booking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FullUserRs {
    private Long id;
    private String name;
    private String email;
    private String role;
    private Instant createdAt;
}