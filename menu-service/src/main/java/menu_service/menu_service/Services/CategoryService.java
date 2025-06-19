package menu_service.menu_service.Services;

import menu_service.menu_service.Models.DTO.CreateCategory;
import menu_service.menu_service.Models.DTO.ResCategory;

import java.util.List;


public interface CategoryService {
    void addCategory(CreateCategory category);
    List<ResCategory> getAllCategories();
    void deleteCategory(String id);

}
