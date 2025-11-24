package User.management.user.management.Web;
import User.management.user.management.Models.DTO.UserChangeRoleDTO;
import User.management.user.management.Models.DTO.UserDetailsDTO;
import User.management.user.management.Models.Enum.Role;
import User.management.user.management.Service.Interfaces.ManagerService;
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
class ManagerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenService tokenService;

    @MockitoBean
    private ManagerService managerService;

    private String generateJwt (String username) {
        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        List.of(() -> "ROLE_MANAGER")
                );
        return tokenService.generateToken(auth);

    }

    @Test
    void testGetProfiles() throws Exception {
        List<UserDetailsDTO> mockedList = List.of(
                new UserDetailsDTO(1L,"user1", "USER"),
                new UserDetailsDTO(2L,"user2", "USER")
        );

        Mockito.when(managerService.getAllUsers("managerUser"))
                .thenReturn(mockedList);

        String jwt = generateJwt("managerUser");

        mockMvc.perform(get("/api/change/personal")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[1].role").value("USER"));
    }


    @Test
    void testChangeProfileSuccess() throws Exception {
        UserChangeRoleDTO dto = new UserChangeRoleDTO("user1", Role.MANAGER);
        String jwt = generateJwt("managerUser");

        mockMvc.perform(post("/api/change/personal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "user1",
                                    "newRole": "MANAGER"
                                }
                                """)
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(content().string("Profile changed"));
        Mockito.verify(managerService).changeProfileRole(any(UserChangeRoleDTO.class));

    }

    @Test
    void testChangeProfileValidationFail() throws Exception {

        String jwt = generateJwt("managerUser");


        mockMvc.perform(post("/api/change/personal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isBadRequest());

    }


    @Test
    void testDeleteProfile() throws Exception {

        String jwt = generateJwt("managerUser");

        mockMvc.perform(delete("/api/change/personal/id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("5")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(content().string("Profile deleted"));

        Mockito.verify(managerService).deleteProfile(eq(5L));
    }
}