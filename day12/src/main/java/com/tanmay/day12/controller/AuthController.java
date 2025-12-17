package com.tanmay.day12.controller;

import com.tanmay.day12.dto.ApiResponse;
import com.tanmay.day12.dto.JWTResponse;
import com.tanmay.day12.dto.LoginRequest;
import com.tanmay.day12.dto.SignupRequest;
import com.tanmay.day12.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication Management API's")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signUp")
    @Operation(summary = "Sign Up")
    public ResponseEntity<@NonNull ApiResponse<String>> signUp(@RequestBody SignupRequest request) {
        String signUpDone = authService.signUp(request);
        return ResponseEntity.ok(ApiResponse.<String>builder().
                success(true)
                .message("Signed up successfully")
                .data(signUpDone)
                .build()
        );
    }

    @PostMapping("/login")
    @Operation(summary = "Login")
    public ResponseEntity<@NonNull ApiResponse<JWTResponse>> login(@RequestBody LoginRequest request) {
        JWTResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.<JWTResponse>builder()
                .success(true)
                .message("Logged in successfully")
                .data(response)
                .build()
        );
    }
}
