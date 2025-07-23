package menu_service.menu_service.Api;

import menu_service.menu_service.Models.DTO.MenuItemRes;
import menu_service.menu_service.Services.MenuItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/getAll")
public class GetItemsController {

    private final MenuItemService menuItemService;


    public GetItemsController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping
    public ResponseEntity<List<MenuItemRes>> getAll() {
       return ResponseEntity.ok( menuItemService.getAll());
    }

    @GetMapping("/byCategory")
    public ResponseEntity<List<MenuItemRes>> getAllByCategory(@RequestBody String category) {

        return ResponseEntity.ok( menuItemService.getAllByCategory(category));
    }



}
