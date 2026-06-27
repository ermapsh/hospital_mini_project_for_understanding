package com.ermapsh.hospital.handlers;

import com.ermapsh.hospital.entity.User;
import com.ermapsh.hospital.service.JwtService;
import com.ermapsh.hospital.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;
    private final JwtService jwtService;

    @Value("${deploy.env}")
    private String env;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) token.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        logger.warn("email: " + email);
        if (email != null) {
            User user = userService.getUsrByEmail(email);
            logger.warn("user" + user);
            if (user == null) {
                user = User.builder()
                        .email(email)
                        .build();


                User getuser = userService.save(user);
                logger.warn("getuser: " + getuser);
                String accessToken = jwtService.generateAccessToken(getuser);
                String refreshToken = jwtService.generateRefreshToken(getuser);

                Cookie cookie = new Cookie("refreshToken", refreshToken);
                cookie.setHttpOnly(true);
                cookie.setSecure(true);
                response.addCookie(cookie);

                String frontendUrl = "http://localhost:8080/home.html?token=" + accessToken;
                logger.warn("Before Redirecting to: {}" + frontendUrl);
                getRedirectStrategy().sendRedirect(request, response, frontendUrl);
                logger.warn("After Redirecting to: {}" + frontendUrl);
            } else {
                String accessToken = jwtService.generateAccessToken(user);
                String refreshToken = jwtService.generateRefreshToken(user);

                logger.info("accessToken=" + accessToken);
                logger.info("refreshToken=" + refreshToken);

                Cookie cookie = new Cookie("refreshToken", refreshToken);
                cookie.setHttpOnly(true);
                cookie.setSecure(true);
                response.addCookie(cookie);

                String frontendUrl = "http://localhost:8080/home.html?token=" + accessToken;
                logger.warn("Before Redirecting to: {}" + frontendUrl);
                getRedirectStrategy().sendRedirect(request, response, frontendUrl);
                logger.warn("After Redirecting to: {}" + frontendUrl);
            }
        }
    }
}
