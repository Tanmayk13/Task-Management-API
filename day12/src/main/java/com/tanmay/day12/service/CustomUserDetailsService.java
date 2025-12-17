package com.tanmay.day12.service;

import com.tanmay.day12.config.CustomUserDetails;
import com.tanmay.day12.entity.User;
import com.tanmay.day12.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username) {
        User user = repo.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return new CustomUserDetails(user);
    }
}

