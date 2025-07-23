package order.services.order.services.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import order.services.order.services.Repositories.TableRepositories;

public class UniqueTableNameValidator implements ConstraintValidator<UniqueTableName, String> {

   private final TableRepositories repositories;

    public UniqueTableNameValidator(TableRepositories repositories) {
        this.repositories = repositories;
    }


    @Override
    public boolean isValid(String tableName, ConstraintValidatorContext constraintValidatorContext) {

        return repositories.findByTableName(tableName).isEmpty();
    }
}
