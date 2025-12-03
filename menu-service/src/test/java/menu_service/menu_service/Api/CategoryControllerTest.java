package menu_service.menu_service.Api;

import com.fasterxml.jackson.databind.ObjectMapper;
import menu_service.menu_service.Models.Category;
import menu_service.menu_service.Models.DTO.CreateCategory;
import menu_service.menu_service.Models.DTO.MenuItemCreate;
import menu_service.menu_service.Models.DTO.ResCategory;
import menu_service.menu_service.Models.DTO.ResStatus;
import menu_service.menu_service.Services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
@Import({menu_service.menu_service.Config.SecurityConfig.class})
class CategoryControllerTest {
    // must skip the validation for unique Category name in CreateCategory.class

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CategoryService categoryService;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    private CreateCategory createCategory;
    private ResCategory resCategory;

    @BeforeEach
    void setUp() {
        createCategory = new CreateCategory("PIZZA");
        resCategory = new ResCategory("1", "PIZZA");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateCategory_Success() throws Exception {
        when(categoryService.addCategory(ArgumentMatchers.any(CreateCategory.class)))
                .thenReturn(new ResStatus("Created"));

        mockMvc.perform(post("/api/menu/category/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createCategory)));
//
//                .andExpect(status().isOk())
//                .andExpect(content().string("Created"));
//        verify(categoryService, times(1)).addCategory(any(CreateCategory.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateCategory_ValidationError() throws Exception {
        CreateCategory invalidItem = new CreateCategory(null);

        mockMvc.perform(post("/api/menu/category/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidItem)));
//                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteCategory_Success() throws Exception {
        when(categoryService.deleteCategory("1")).thenReturn("Category PIZZA is Deleted!");

        mockMvc.perform(delete("/api/menu/category/delete/1"))
                .andExpect(status().isOk());

        verify(categoryService, times(1)).deleteCategory("1");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllCategories() throws Exception {
        mockMvc.perform(get("/api/menu/category/getAll"))
                .andExpect(status().isOk());
    }





}