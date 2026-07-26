package com.ermapsh.hospital.service;

import com.ermapsh.hospital.dto.SignupRequest;
import com.ermapsh.hospital.dto.SignupResponse;
import com.ermapsh.hospital.entity.User;
import com.ermapsh.hospital.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final String cacheKey= "users";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    @Cacheable(cacheNames = cacheKey, key = "#email")
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

    @Cacheable(cacheNames = cacheKey, key = "#email")
    public SignupResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()->  new BadCredentialsException("User not found" + email));
        return modelMapper.map(user, SignupResponse.class);
    }

    @CachePut(cacheNames = cacheKey, key = "#request.email")
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
        User savedUser = save(toCreate);
        return modelMapper.map(savedUser, SignupResponse.class);
    }

    //    extra method same like signup
    //    @CachePut(cacheNames = cacheKey, key = "#result.email") // commenting for testing while using
    public User save(User newUser) {
        return userRepository.save(newUser);
    }

    public SignupResponse getUserById2(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User with id " + id +
                " not found"));
        return modelMapper.map(user, SignupResponse.class);

    }

    @CachePut(cacheNames = cacheKey, key = "#result.email")
    @Transactional
    public SignupResponse updateUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        user.setName("ErMapsh is great 2");
        user.setRoles(new HashSet<>(Set.of()));
        user = userRepository.save(user);
        return new SignupResponse(
              user.getId(),
              user.getEmail(),
              user.getName(),
              user.getRoles().toString()
        );
    }


    @Transactional
    @CacheEvict(cacheNames = cacheKey, key = "#email")
    public boolean deleteUser(String email){
        User user = userRepository.findByEmail(email)

                .orElseThrow(() ->

                        new UsernameNotFoundException("User not found with email: " + email));

        userRepository.delete(user);
        return true;
    }
}
