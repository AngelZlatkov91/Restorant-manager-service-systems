package User.management.user.management.Service.impl;

import User.management.user.management.Models.Entitys.User;
import User.management.user.management.Models.Enum.Role;
import User.management.user.management.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;


import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

public class UserServiceDetailsTest {

    private UserRepository userRepository;
    private UserServiceDetails userServiceDetails;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userServiceDetails = new UserServiceDetails(userRepository);
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        // Arrange
        User mockUser = new User();
        mockUser.setUsername("john");
        mockUser.setPassword("encodedPass");
        mockUser.setRole(Role.ADMIN);  // промени според твоя enum

        when(userRepository.findByUsername("john")).thenReturn(Optional.of(mockUser));

        // Act
        UserDetails userDetails = userServiceDetails.loadUserByUsername("john");

        // Assert
        assertNotNull(userDetails);
        assertEquals("john", userDetails.getUsername());
        assertEquals("encodedPass", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));

        verify(userRepository, times(1)).findByUsername("john");
    }



    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Arrange
        when(userRepository.findByUsername("missing")).thenReturn(Optional.empty());

        // Assert + Act
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userServiceDetails.loadUserByUsername("missing")
        );

        assertEquals("User not found with missing", exception.getMessage());

        verify(userRepository, times(1)).findByUsername("missing");
    }
}
