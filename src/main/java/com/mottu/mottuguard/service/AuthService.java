package com.mottu.mottuguard.service;

import com.mottu.mottuguard.dto.auth.*;
import com.mottu.mottuguard.models.Role;
import com.mottu.mottuguard.models.User;
import com.mottu.mottuguard.repository.RoleRepo;
import com.mottu.mottuguard.repository.UserRepo;
import com.mottu.mottuguard.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private RoleRepo roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse login(LoginRequest request) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // Load user
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate tokens
        String token = tokenProvider.generateToken(user.getEmail(), user.getName(), user.getId());
        String refreshToken = tokenProvider.generateRefreshToken();

        // Save refresh token
        user.setRefreshToken(refreshToken);
        user.setRefreshTokenExpiryTime(Instant.now().plusMillis(tokenProvider.getRefreshExpirationTime()));
        userRepository.save(user);

        return new AuthResponse(token, refreshToken, user.getEmail(), user.getName());
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Check if user already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // Create new user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Assign default USER role
        Role userRole = roleRepository.findByName("USER")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("USER");
                    return roleRepository.save(newRole);
                });

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        // Save user
        user = userRepository.save(user);

        // Generate tokens
        String token = tokenProvider.generateToken(user.getEmail(), user.getName(), user.getId());
        String refreshToken = tokenProvider.generateRefreshToken();

        // Save refresh token
        user.setRefreshToken(refreshToken);
        user.setRefreshTokenExpiryTime(Instant.now().plusMillis(tokenProvider.getRefreshExpirationTime()));
        userRepository.save(user);

        return new AuthResponse(token, refreshToken, user.getEmail(), user.getName());
    }

    @Transactional
    public AuthResponse refresh(RefreshRequest request) {
        // Find user by refresh token
        User user = userRepository.findByRefreshToken(request.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        // Check if refresh token is expired
        if (user.getRefreshTokenExpiryTime().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        // Generate new tokens
        String token = tokenProvider.generateToken(user.getEmail(), user.getName(), user.getId());
        String refreshToken = tokenProvider.generateRefreshToken();

        // Update refresh token
        user.setRefreshToken(refreshToken);
        user.setRefreshTokenExpiryTime(Instant.now().plusMillis(tokenProvider.getRefreshExpirationTime()));
        userRepository.save(user);

        return new AuthResponse(token, refreshToken, user.getEmail(), user.getName());
    }
}
