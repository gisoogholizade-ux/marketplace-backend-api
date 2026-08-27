package com.gisoo.marketplace.auth;

import com.gisoo.marketplace.security.JwtService;
import com.gisoo.marketplace.user.User;
import com.gisoo.marketplace.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(userRepository, passwordEncoder, jwtService);
    }

    @Test
    void registerCreatesCustomerWithNormalizedEmailAndEncodedPassword() {
        when(userRepository.existsByEmailIgnoreCase("gisoo@example.com")).thenReturn(false);
        when(passwordEncoder.encode("strongPass123")).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User user = authService.register(new RegisterRequest(
                "Gisoo Gholizade",
                "  GISOO@EXAMPLE.COM  ",
                "strongPass123"
        ));

        assertEquals("gisoo@example.com", user.getEmail());
        assertEquals("encoded", user.getPasswordHash());
        assertEquals("CUSTOMER", user.getRole().name());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerRejectsDuplicateEmail() {
        when(userRepository.existsByEmailIgnoreCase("gisoo@example.com")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.register(new RegisterRequest(
                        "Gisoo",
                        "gisoo@example.com",
                        "strongPass123"
                ))
        );

        assertEquals("Email is already registered", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }
}
