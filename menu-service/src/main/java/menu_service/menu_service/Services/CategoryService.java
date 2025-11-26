package menu_service.menu_service.Services;

import menu_service.menu_service.Models.DTO.CreateCategory;
import menu_service.menu_service.Models.DTO.ResCategory;
import menu_service.menu_service.Models.DTO.ResStatus;

import java.util.List;


public interface CategoryService {
    ResStatus addCategory(CreateCategory category);
    List<ResCategory> getAllCategories();
    String deleteCategory(String id);

}
