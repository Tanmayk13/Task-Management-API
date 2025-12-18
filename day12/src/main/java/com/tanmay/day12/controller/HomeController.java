package com.tanmay.day12.controller;

import com.tanmay.day12.dto.ApiResponse;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {
    public ResponseEntity<@NonNull ApiResponse<@NonNull String>> greet() {
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Greeting from Task management API")
                        .data("Hello World!")
                        .build()
        );
    }
}
