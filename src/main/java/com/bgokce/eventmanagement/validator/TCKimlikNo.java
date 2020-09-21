package com.bgokce.eventmanagement.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TCNoValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TCKimlikNo {
    String message() default "Tc kimlik numarası formatı uygun değildir";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}