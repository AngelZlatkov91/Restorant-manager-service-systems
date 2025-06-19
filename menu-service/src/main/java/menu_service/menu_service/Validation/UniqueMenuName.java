package menu_service.menu_service.Validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueMenuNameValidator.class)
public @interface UniqueMenuName {
    String message() default "The Item name should be unique";
    Class<?>[] groups()default {};

    Class<? extends Payload>[] payload() default {};

}
