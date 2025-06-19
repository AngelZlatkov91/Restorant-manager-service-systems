package menu_service.menu_service.Services;

import menu_service.menu_service.Models.Category;
import menu_service.menu_service.Models.DTO.CreateCategory;
import menu_service.menu_service.Models.DTO.ResCategory;
import menu_service.menu_service.Repositories.CategoryItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {


    private final CategoryItemRepository categoryItemRepository;

    public CategoryServiceImpl(CategoryItemRepository categoryItemRepository) {
        this.categoryItemRepository = categoryItemRepository;
    }

    @Override
    public void addCategory(CreateCategory category) {
        Category newCategory = new Category();
        newCategory.setName(category.getCategoryName());
        categoryItemRepository.save(newCategory);
    }

    @Override
    public List<ResCategory> getAllCategories() {
        return categoryItemRepository.findAll().stream().map(this::getCategoryByName).collect(Collectors.toList());
    }


    private ResCategory getCategoryByName(Category category) {
        ResCategory createCategory = new ResCategory();
        createCategory.setCategory(category.getName());
        return createCategory;
    }

    @Override
    @Transactional
    public void deleteCategory(String id) {
         categoryItemRepository.deleteById(id);
    }
}
