package com.ermapsh.hospital.service;

import com.ermapsh.hospital.dto.LoginDto;
import com.ermapsh.hospital.dto.SignupRequest;
import com.ermapsh.hospital.dto.SignupResponse;
import com.ermapsh.hospital.entity.User;
import com.ermapsh.hospital.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User with id " + userId +
                " not found"));
    }

    public User getUsrByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }


    public SignupResponse signUp(SignupRequest request) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()) {
            throw new BadCredentialsException("User email already exist " + request.getEmail());
        }

        User toCreate = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .name(request.getName())
                .roles(request.getRoles())
                .build();
        toCreate.setPassword(passwordEncoder.encode(toCreate.getPassword()));
        User savedUser = userRepository.save(toCreate);
        return modelMapper.map(savedUser, SignupResponse.class);
    }

    public User save(User newUser) {
        return userRepository.save(newUser);
    }
}
