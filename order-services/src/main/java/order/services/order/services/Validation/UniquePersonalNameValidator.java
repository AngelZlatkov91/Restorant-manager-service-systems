package order.services.order.services.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import order.services.order.services.Repositories.PersonalRepositories;

public class UniquePersonalNameValidator implements ConstraintValidator<UniquePersonalName, String> {

    private final PersonalRepositories personalRepositories;

    public UniquePersonalNameValidator(PersonalRepositories personalRepositories) {
        this.personalRepositories = personalRepositories;
    }


    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return personalRepositories.findByName(name).isEmpty();
    }
}
