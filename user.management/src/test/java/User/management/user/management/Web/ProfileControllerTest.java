package User.management.user.management.Web;

import User.management.user.management.Models.DTO.UserDetailsDTO;
import User.management.user.management.Models.Enum.Role;
import User.management.user.management.Service.Interfaces.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProfileService profileService;

    @Test
    @WithMockUser(username = "john")
    void testGetProfile_ReturnsUSerDetails() throws Exception {
        UserDetailsDTO userDetails = new UserDetailsDTO(1L,"john", Role.ADMIN.name());

        when(profileService.getUserDetails("john")).thenReturn(userDetails);

        mockMvc.perform(get("/api/profile")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john"))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    @Test

    void testGetProfile_ReturnsNotExistUser() throws Exception {
        UserDetailsDTO userDetails = new UserDetailsDTO(1L,"john", Role.ADMIN.name());

        when(profileService.getUserDetails("john")).thenReturn(userDetails);

        mockMvc.perform(get("/api/profile")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

}