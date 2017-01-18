package com.manonline.examples.bean.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by davidqi on 1/18/17.
 */
public class NotEmptyValidator implements ConstraintValidator<NotEmpty, String>{

    public void initialize(NotEmpty parameters) {

    }

    public boolean isValid(String string,
                           ConstraintValidatorContext constraintValidatorContext) {

        if (string != null && !string.isEmpty()) {
            return true;
        }

        return false;
    }
}