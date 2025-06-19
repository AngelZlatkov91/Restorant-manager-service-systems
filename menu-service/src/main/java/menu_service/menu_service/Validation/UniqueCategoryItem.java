package menu_service.menu_service.Validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueCategoryItemValidator.class)
public @interface UniqueCategoryItem {
    String message() default "The category should be unique";
    Class<?>[] groups()default {};

    Class<? extends Payload>[] payload() default {};

}
