package order.services.order.services.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import order.services.order.services.Repositories.PersonalRepositories;

public class UniquePersonalPasswordValidator implements ConstraintValidator<UniquePersonalPassword, String> {

  private final PersonalRepositories personalRepositories;

    public UniquePersonalPasswordValidator(PersonalRepositories personalRepositories) {
        this.personalRepositories = personalRepositories;
    }


    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return personalRepositories.findByPassword(password).isEmpty();
    }
}
