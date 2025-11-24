package User.management.user.management.Web;

import User.management.user.management.Models.DTO.UserLoginDTO;
import User.management.user.management.Service.Interfaces.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserLoginControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void login_WithValid_Returns200_AndToken() throws  Exception{
        UserLoginDTO dto = new UserLoginDTO("john","password123");

        Authentication authMock = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authMock);
        when(tokenService.generateToken(any())).thenReturn("jwt-token-123");

        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Login successful"))
                .andExpect(jsonPath("$.token").value("jwt-token-123"));

        verify(authenticationManager, times(1)).authenticate(any());
        verify(tokenService, times(1)).generateToken(any());

    }

    @Test
    void login_WhenInvalid_Returns400() throws  Exception{
        UserLoginDTO invalidDTO = new UserLoginDTO();
        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());

        verify(authenticationManager, times(0)).authenticate(any());
        verify(tokenService, times(0)).generateToken(any());
    }

}