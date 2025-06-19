package menu_service.menu_service.Api;

import jakarta.validation.Valid;
import menu_service.menu_service.Models.DTO.MenuItemCreate;
import menu_service.menu_service.Models.DTO.MenuItemRes;
import menu_service.menu_service.Services.MenuItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/menu/item")
public class ItemController {

    private final MenuItemService menuItemService;

    public ItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }


    @PostMapping("/create")
    public ResponseEntity<String> createItem(@RequestBody @Valid MenuItemCreate menuItemCreate, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
      return ResponseEntity.ok(  menuItemService.createMenuItem(menuItemCreate));
    }

    @GetMapping("/itemId")
    public ResponseEntity<MenuItemRes> getById(@RequestBody String itemId) {
        return ResponseEntity.ok(menuItemService.getMenuItem(itemId));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteItem(@RequestBody String itemId) {
        menuItemService.deleteMenuItem(itemId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateItem(@RequestBody MenuItemRes menuItemRes, BindingResult bindingResult) {

        MenuItemRes menuItemRes1 = menuItemService.updateMenuItem(menuItemRes);

        return ResponseEntity.ok("Item updated successfully");
    }

}
