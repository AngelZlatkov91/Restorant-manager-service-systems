package menu_service.menu_service.Validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import menu_service.menu_service.Repositories.CategoryItemRepository;

public class UniqueCategoryItemValidator implements ConstraintValidator<UniqueCategoryItem, String> {

    private final CategoryItemRepository categoryItemRepository;

    public UniqueCategoryItemValidator(CategoryItemRepository categoryItemRepository) {

        this.categoryItemRepository = categoryItemRepository;
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return !categoryItemRepository.existsByName(value);
    }
}
