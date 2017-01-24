package com.manonline.examples.bean.validation;

import com.manonline.examples.bean.validation.validator.NotEmpty;

import java.lang.reflect.Method;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.executable.ExecutableValidator;

/**
 * Created by davidqi on 1/18/17.
 * reference : http://www.ibm.com/developerworks/cn/java/j-lo-beanvalid/
 * reference : http://ifeve.com/use-bean-validation/
 */
public class BeanValidatorTest {

    public static class Car {

        private String name;

        // 属性
        @NotEmpty(message = "type string cannot be empty")
        private String type;

        // 方法返回值
        @NotNull(message = "车主不能为空")
        public String getRentalStation() {
            return name;
        }

        // 方法参数
        public void drive(@Max(75)int speedInMph) {

        }
    }

    public static void main(String[] args) {

        Car car = new Car();

        // 验证Bean参数，并返回验证结果信息
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Car>> validators = validator.validate(car);
        for (ConstraintViolation<Car> constraintViolation : validators) {
            System.out.println(constraintViolation.getMessage());
        }

        // 验证方法参数
        Method method = null;
        try {
            method = Car.class.getMethod("drive", int.class);
        } catch (SecurityException e) {
        } catch (NoSuchMethodException e) {
        }
        Object[] parameterValues = {80};

        ExecutableValidator executableValidator = validator.forExecutables();
        Set<ConstraintViolation<Car>> methodValidators = executableValidator.validateParameters(car,
                method, parameterValues);
        for (ConstraintViolation<Car> constraintViolation : methodValidators) {
            System.out.println(constraintViolation.getMessage());
        }
    }
}
