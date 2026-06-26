package com.ermapsh.hospital.config;

import com.ermapsh.hospital.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private  final JwtAuthFilter jwtAuthFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        httpSecurity.
                addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).
                csrf(csrfConfig->csrfConfig.disable()).
                sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                authorizeHttpRequests(auth->auth.
                        requestMatchers("/auth/**").permitAll().
//                        requestMatchers("/post/**").hasRole("ADMIN").
                        anyRequest().authenticated());

//                .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }


    // testing purpose
    /*
    @Bean
    UserDetailsService myInMemoryUserDetailsService(){
        UserDetails adminUser = User.withUsername("Mahesh").
                password(passwordEncoder().encode("mahesh Mestri")).roles("ADMIN")
                .build();

        UserDetails normalUser = User.withUsername("someone").
                password(passwordEncoder().encode("someone")).roles("USER")
                .build();

        return new InMemoryUserDetailsManager(normalUser, adminUser);
    }
     */

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

}
