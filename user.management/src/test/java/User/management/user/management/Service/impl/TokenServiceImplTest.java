package User.management.user.management.Service.impl;

import User.management.user.management.Models.Entitys.User;
import User.management.user.management.Models.Enum.Role;
import User.management.user.management.Repositories.UserRepository;
import User.management.user.management.Service.Interfaces.TokenService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TokenServiceImplTest {
    private JwtEncoder jwtEncoder;
    private TokenService tokenService;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        jwtEncoder = mock(JwtEncoder.class);
        tokenService = new TokenServiceImpl(jwtEncoder);

    }


    @Test
    void generateToken_ShouldCreateTokenWithCorrectClaims() {

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("john");

        Jwt jwt = mock(Jwt.class);
        when(jwt.getTokenValue()).thenReturn("mocked-jwt-token");

        when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(jwt);

        String resultToken = tokenService.generateToken(auth);

        assertEquals("mocked-jwt-token", resultToken);

        ArgumentCaptor<JwtEncoderParameters> captor = ArgumentCaptor.forClass(JwtEncoderParameters.class);
        verify(jwtEncoder).encode(captor.capture());

        JwtClaimsSet claims = captor.getValue().getClaims();


        assertEquals("john", claims.getSubject());

        String scope = claims.getClaim("scope").toString();

        assertFalse(scope.contains("ROLE_ADMIN"));


        Instant issuedAt = claims.getIssuedAt();
        Instant expiresAt = claims.getExpiresAt();

        assertNotNull(issuedAt);
        assertNotNull(expiresAt);
        assertTrue(expiresAt.isAfter(issuedAt));
    }



}