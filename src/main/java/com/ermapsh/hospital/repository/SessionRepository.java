package com.ermapsh.hospital.repository;

import com.ermapsh.hospital.entity.Session;
import com.ermapsh.hospital.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findByUser(User user);
    List<Session> findByUserAndEnabledTrue(User user);
    Optional<Session> findByRefreshToken(String refreshToken);
    Optional<Session> findByRefreshTokenAndEnabledTrue(String refreshToken);
}