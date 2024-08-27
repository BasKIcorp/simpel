package org.simpel.pumpingUnits.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TemperatureValidator.class)
public @interface ValidTemperature {
    String message() default "Inadmissible temperature for the selected coolant type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}