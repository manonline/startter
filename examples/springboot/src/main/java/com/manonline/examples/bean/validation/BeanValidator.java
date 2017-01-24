package com.manonline.examples.bean.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;

import static com.google.common.collect.Iterables.getLast;

/**
 * Created by davidqi on 1/18/17.
 */
public class BeanValidator {

    public static <T> void validate(T object) {

        // 获得验证器
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        // 执行验证
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);

        // 如果有验证信息，则将最后一个取出来包装成异常返回
        ConstraintViolation<T> constraintViolation = getLast(constraintViolations);

        if (constraintViolation != null) {
            throw new ValidationException(constraintViolation.toString());
        }
    }
}
