package com.ermapsh.hospital.service;

import com.ermapsh.hospital.entity.Session;
import com.ermapsh.hospital.entity.User;
import com.ermapsh.hospital.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionService {

    private final SessionRepository sessionRepository;


    public Session generateNewSession(User user, String refreshToken) {
        List<Session> sessions = sessionRepository.findByUserAndEnabledTrue(user);
        int sessionLimit = 2;
        if (sessions.size() >= sessionLimit) {
            Session oldestSession = sessions.stream()
                    .min(Comparator.comparing(Session::getLastUsedAt))
                    .orElseThrow();
            deleteSession(oldestSession.getRefreshToken());
        }
        Session session = Session.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();

        return sessionRepository.save(session);
    }

    public void validateSession(String refreshToken) {

        log.warn("1. Looking up session");

        Optional<Session> optional =
                sessionRepository.findByRefreshTokenAndEnabledTrue(refreshToken);

        log.warn("2. Query executed");

        if (optional.isEmpty()) {
            log.error("3. Session not found");
            throw new RuntimeException("Session not found");
        }

        log.warn("4. Session found");

        Session session = optional.get();

        session.setLastUsedAt(LocalDateTime.now());

        sessionRepository.save(session);

        log.warn("5. Session updated");
    }
    public Session deleteSession(String refreshToken) {
        Session session = sessionRepository.findByRefreshTokenAndEnabledTrue(refreshToken).
                orElseThrow(() -> new SessionAuthenticationException("Session not found for refreshToken: " + refreshToken));

        session.setEnabled(false);
        sessionRepository.save(session);
        return session;
    }
}
