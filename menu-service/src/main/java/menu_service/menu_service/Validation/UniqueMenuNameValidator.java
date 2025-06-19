package menu_service.menu_service.Validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import menu_service.menu_service.Repositories.MenuItemRepository;

public class UniqueMenuNameValidator implements ConstraintValidator<UniqueMenuName, String> {

    private final MenuItemRepository menuItemRepository;

    public UniqueMenuNameValidator(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return !menuItemRepository.getByName(value);
    }
}
