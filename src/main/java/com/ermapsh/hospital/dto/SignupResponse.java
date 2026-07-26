package com.ermapsh.hospital.dto;

import com.ermapsh.hospital.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupResponse implements Serializable {
    private Long id;
    private String email;
    private String name;
    private String roles;
}
