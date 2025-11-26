package menu_service.menu_service.Api;

import menu_service.menu_service.Models.DTO.MenuItemRes;
import menu_service.menu_service.Models.TypeProduct;
import menu_service.menu_service.Services.MenuItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GetItemsController.class)
@AutoConfigureMockMvc(addFilters = false)
class GetItemsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MenuItemService menuItemService;

    @Test
    void testGetAll() throws Exception {
        List<MenuItemRes> mockItems = List.of(
                new MenuItemRes("1","POLO",10.5,"Pizza", TypeProduct.KITCHEN,true),
                new MenuItemRes("2","MADRI",5.5,"BEER", TypeProduct.BAR,true)
        );

        when(menuItemService.getAll()).thenReturn(mockItems);

        mockMvc.perform(get("/api/getAll")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("POLO"))
                .andExpect(jsonPath("$[1].name").value("MADRI"));
    }

    @Test
    void testGetAllByCategory() throws Exception {
        List<MenuItemRes> mockItems = List.of(
                new MenuItemRes("1","POLO",10.5,"Food", TypeProduct.KITCHEN,true)
        );

        when(menuItemService.getAllByCategory("Food")).thenReturn(mockItems);

        mockMvc.perform(get("/api/getAll/byCategory")
                        .param("category", "Food")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].category").value("Food"))
                .andExpect(jsonPath("$[0].name").value("POLO"));
    }
}
