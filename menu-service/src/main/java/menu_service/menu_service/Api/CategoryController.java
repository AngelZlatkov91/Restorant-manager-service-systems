package menu_service.menu_service.Api;

import jakarta.validation.Valid;
import menu_service.menu_service.Models.DTO.CreateCategory;
import menu_service.menu_service.Models.DTO.ItemId;
import menu_service.menu_service.Models.DTO.ResCategory;
import menu_service.menu_service.Services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu/category")
public class CategoryController {

    private final CategoryService categoryService;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;

    }

    @PostMapping("/create")
    public ResponseEntity<String> createCategory(@RequestBody @Valid  CreateCategory createCategory, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        categoryService.addCategory(createCategory);
        return ResponseEntity.ok("Successfully created category - " + createCategory.getCategoryName());
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<ResCategory>> getAllCategory() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCategory(@RequestBody ItemId categoryId) {
        categoryService.deleteCategory(categoryId.getItemId());
        return ResponseEntity.ok("Successfully deleted category - " + categoryId);
    }
}
