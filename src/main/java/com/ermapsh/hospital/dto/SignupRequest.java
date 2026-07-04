package com.ermapsh.hospital.dto;

import com.ermapsh.hospital.enums.Permission;
import com.ermapsh.hospital.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequest {

    @NotNull(message = "email required")
    private String email;

    @NotNull(message = "password required")
    private String password;

    @NotNull(message = "name required")
    private String name;

    @NotNull
    private Set<Role> roles;

    private Set<Permission> permissions;
}
