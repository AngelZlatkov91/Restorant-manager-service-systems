package User.management.user.management.Web;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
class ExceptionAdviceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @RestController
    static class TestController {

        @GetMapping("/test/jwt")
        public void throwJwt() {
            throw new org.springframework.security.oauth2.jwt.JwtException("JWT invalid");
        }

        @GetMapping("/test/notfound")
        public void throwNotFound() {
            throw new jakarta.persistence.EntityNotFoundException("User not found");
        }

        @GetMapping("/test/illegal")
        public void throwIllegal() {
            throw new IllegalArgumentException("Illegal argument");
        }

        @GetMapping("/test/validation")
        public void throwValidation() throws Exception {
            throw new org.springframework.web.bind.MethodArgumentNotValidException(
                    null,
                    null
            );
        }

        @GetMapping("/test/generic")
        public void throwGeneric() {
            throw new RuntimeException("Generic error");
        }
    }

    @Test
    void testHandleJwtException() throws Exception {
        mockMvc.perform(get("/test/jwt"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testHandleNotFoundException() throws Exception {
        mockMvc.perform(get("/test/notfound"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testHandleIllegalArgumentException() throws Exception {
        mockMvc.perform(get("/test/illegal"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testHandleGenericException() throws Exception {
        mockMvc.perform(get("/test/generic"))
                .andExpect(status().is4xxClientError());
    }

}