package order.services.order.services.Validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniquePersonalNameValidator.class)
public @interface UniquePersonalName {
    String message() default "The Personal name should be unique";
    Class<?>[] groups()default {};

    Class<? extends Payload>[] payload() default {};

}
