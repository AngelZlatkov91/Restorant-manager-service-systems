package menu_service.menu_service.Services;

import menu_service.menu_service.Models.Category;
import menu_service.menu_service.Models.DTO.CreateCategory;
import menu_service.menu_service.Models.DTO.ResCategory;
import menu_service.menu_service.Models.DTO.ResStatus;
import menu_service.menu_service.Repositories.CategoryItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


class CategoryServiceImplTest {
    private CategoryItemRepository repository;
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        repository = mock(CategoryItemRepository.class);
        categoryService = new CategoryServiceImpl(repository);
    }

    @Test
    void addCategory_ShouldSaveCategoryAndReturnStatus() {

        CreateCategory createDto = new CreateCategory();
        createDto.setCategoryName("Pizza");

        ResStatus result = categoryService.addCategory(createDto);

        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
        verify(repository, times(1)).save(captor.capture());

        Category saved = captor.getValue();
        assertThat(saved.getName()).isEqualTo("Pizza");
        assertThat(result.getStatus()).isEqualTo("Is Created");
    }

    @Test
    void getAllCategories_ShouldReturnMappedDTOs() {

        Category c1 = new Category("1", "Drinks");
        Category c2 = new Category("2", "Desserts");

        when(repository.findAll()).thenReturn(List.of(c1, c2));

        List<ResCategory> result = categoryService.getAllCategories();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo("1");
        assertThat(result.get(0).getCategory()).isEqualTo("Drinks");
        assertThat(result.get(1).getId()).isEqualTo("2");
        assertThat(result.get(1).getCategory()).isEqualTo("Desserts");
    }

    @Test
    void deleteCategory_ShouldCallRepositoryDelete() {
        Category newCategory = new Category("c1","Food");
        when(repository.findById("c1")).thenReturn(Optional.of(newCategory));
        String result = categoryService.deleteCategory("c1");
        assertThat(result).isEqualTo("Category Food is Deleted!");
    }

}