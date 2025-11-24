package User.management.user.management.Web;

import User.management.user.management.Models.DTO.UserChangeRoleDTO;
import User.management.user.management.Models.DTO.UserDetailsDTO;
import User.management.user.management.Models.Enum.Role;
import User.management.user.management.Service.Interfaces.AdminService;
import User.management.user.management.Service.Interfaces.TokenService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenService tokenService;

    @MockitoBean
    private AdminService adminService;

    private String generateJwt (String username) {
        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        List.of(() -> "ROLE_ADMIN")
                );
        return tokenService.generateToken(auth);

    }

    @Test
    void testGetProfiles() throws Exception {
        List<UserDetailsDTO> mockedList = List.of(
                new UserDetailsDTO(1L,"user1", "USER"),
                new UserDetailsDTO(2L,"user2", "ADMIN")
        );

        Mockito.when(adminService.getAllUsers("adminUser"))
                .thenReturn(mockedList);

        String jwt = generateJwt("adminUser");

        mockMvc.perform(get("/api/change/manager")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[1].role").value("ADMIN"));
    }


    @Test
    void testChangeProfileSuccess() throws Exception {
        UserChangeRoleDTO dto = new UserChangeRoleDTO("user1", Role.ADMIN);
        String jwt = generateJwt("adminUser");

        mockMvc.perform(post("/api/change/manager")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                                {
                                    "username": "user1",
                                    "newRole": "ADMIN"
                                }
                                """)
                .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(content().string("Profile changed"));
        Mockito.verify(adminService).changeProfileRole(any(UserChangeRoleDTO.class));

    }

    @Test
    void testChangeProfileValidationFail() throws Exception {

        String jwt = generateJwt("adminUser");


         mockMvc.perform(post("/api/change/manager")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isBadRequest());

    }


    @Test
    void testDeleteProfile() throws Exception {

        String jwt = generateJwt("adminUser");

        mockMvc.perform(delete("/api/change/manager/id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("5")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(content().string("Profile deleted"));

        Mockito.verify(adminService).deleteProfile(eq(5L));
    }

}