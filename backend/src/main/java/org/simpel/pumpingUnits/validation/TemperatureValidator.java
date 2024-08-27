package org.simpel.pumpingUnits.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.simpel.pumpingUnits.model.installation.GMInstallation;
import org.simpel.pumpingUnits.model.enums.CoolantType;

public class TemperatureValidator implements ConstraintValidator<ValidTemperature, GMInstallation> {

    @Override
    public void initialize(ValidTemperature constraintAnnotation) {
    }

    @Override
    public boolean isValid(GMInstallation gmInstallation, ConstraintValidatorContext context) {
        if (gmInstallation == null || gmInstallation.getCoolantType() == null) {
            return true;
        }

        int temperature = gmInstallation.getTemperature();
        CoolantType coolantType = gmInstallation.getCoolantType();

        if (coolantType == CoolantType.WATER) {
            return temperature >= 4 && temperature <= 70;
        } else {
            return temperature >= -10 && temperature <= 70;
        }
    }
}