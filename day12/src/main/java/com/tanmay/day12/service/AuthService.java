package com.tanmay.day12.service;

import com.tanmay.day12.config.JWTUtils;
import com.tanmay.day12.dto.JWTResponse;
import com.tanmay.day12.dto.LoginRequest;
import com.tanmay.day12.dto.SignupRequest;
import com.tanmay.day12.entity.User;
import com.tanmay.day12.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;

    public String signUp(SignupRequest request) {
        User user = new User();
        user.setUserName(request.getUserName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_USER");
        repo.save(user);
        return "User Created";
    }

    public JWTResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );

        String token = jwtUtils.generateToken(request.getUserName());

        return new JWTResponse(token);
    }
}
