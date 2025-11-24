package User.management.user.management.Web;

import User.management.user.management.Models.DTO.UserRegisterDTO;
import User.management.user.management.Service.Interfaces.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserRegisterControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    private MockMvc mockMvc;

    @Test
    void registerUser_WhenValid_Returns200() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        UserRegisterDTO dto = new UserRegisterDTO("Angel", "12345", "12345");
        mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(userService,times(1)).registerUser(any(UserRegisterDTO.class));
    }

    @Test
    void registerUser_WhitEmptyData() throws Exception{
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        UserRegisterDTO dto = new UserRegisterDTO();
        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.notNullValue()));

        verify(userService,times(0)).registerUser(any());
    }

    @Test
    void registerUser_WhitNotMatchPassword() throws Exception{
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        UserRegisterDTO dto = new UserRegisterDTO("Test1","12345","11111");
        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.notNullValue()));

        verify(userService,times(0)).registerUser(any());
    }



}