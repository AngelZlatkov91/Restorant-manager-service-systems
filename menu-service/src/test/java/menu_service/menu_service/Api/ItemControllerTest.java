package menu_service.menu_service.Api;

import com.fasterxml.jackson.databind.ObjectMapper;
import menu_service.menu_service.Models.DTO.MenuItemCreate;
import menu_service.menu_service.Models.DTO.MenuItemRes;
import menu_service.menu_service.Models.DTO.ResStatus;
import menu_service.menu_service.Models.TypeProduct;
import menu_service.menu_service.Services.MenuItemService;
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


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemController.class)
@Import({menu_service.menu_service.Config.SecurityConfig.class})
public class ItemControllerTest {
  // must skip the validation for unique Item name in MenuItemCreate.class
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MenuItemService menuItemService;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    private MenuItemCreate menuItemCreate;
    private MenuItemRes menuItemRes;

    @BeforeEach
    void setUp() {
        menuItemCreate = new MenuItemCreate("Pizza", 12.5, "Food", TypeProduct.KITCHEN);
        menuItemRes = new MenuItemRes("1", "Pizza", 12.5, "Food", TypeProduct.KITCHEN, true);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateItem_Success() throws Exception {
        when(menuItemService.createMenuItem(ArgumentMatchers.any(MenuItemCreate.class)))
                .thenReturn("Created");

        mockMvc.perform(post("/api/menu/item/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(menuItemCreate)))
                .andExpect(status().isOk())
                .andExpect(content().string("Created"));

        verify(menuItemService, times(1)).createMenuItem(any(MenuItemCreate.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateItem_ValidationError() throws Exception {
        MenuItemCreate invalidItem = new MenuItemCreate("", -5.0, "", null);

        mockMvc.perform(post("/api/menu/item/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidItem)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetById_Success() throws Exception {
        when(menuItemService.getMenuItem("1")).thenReturn(menuItemRes);

        mockMvc.perform(get("/api/menu/item/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Pizza"));

        verify(menuItemService, times(1)).getMenuItem("1");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteItem_Success() throws Exception {
        when(menuItemService.deleteMenuItem("1")).thenReturn(new ResStatus("Deleted"));

        mockMvc.perform(delete("/api/menu/item/delete/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Deleted"));

        verify(menuItemService, times(1)).deleteMenuItem("1");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateItem_Success() throws Exception {
        when(menuItemService.updateMenuItem(any(MenuItemRes.class))).thenReturn(menuItemRes);

        mockMvc.perform(put("/api/menu/item/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(menuItemRes)))
                .andExpect(status().isOk())
                .andExpect(content().string("Item updated successfully"));

        verify(menuItemService, times(1)).updateMenuItem(any(MenuItemRes.class));
    }

    @Test
    void testAccessDenied_WithoutAuth() throws Exception {
        mockMvc.perform(get("/api/menu/item/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void testAccessDenied_Forbidden() throws Exception {
        mockMvc.perform(get("/api/menu/item/1"))
                .andExpect(status().isForbidden());
    }

}
