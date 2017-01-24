package com.manonline.examples.bean.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import static org.junit.Assert.assertEquals;


/**
 * Created by davidqi on 1/18/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BeanValidatorTest2 {

    public static class Car {
        private String name;

        @NotNull(message = "车主不能为空")
        public String getRentalStation() {
            return name;
        }

        public void drive(@Max(75) int speedInMph) {
        }
    }

    @Test
    public void test() {
        try {
            BeanValidator.validate(new Car());
        } catch (Exception e) {
            assertEquals("ConstraintViolationImpl{interpolatedMessage='车主不能为空', propertyPath=rentalStation, rootBeanClass=class com.manonline.examples.bean.validation.BeanValidatorTest2$Car, messageTemplate='车主不能为空'}", e.getMessage());
        }
    }
}
