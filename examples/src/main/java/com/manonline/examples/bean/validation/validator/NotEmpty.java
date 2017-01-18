package com.manonline.examples.bean.validation.validator;

import com.manonline.examples.bean.validation.validator.NotEmptyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by davidqi on 1/18/17.
 */
// 约束注解应用的目标元素类型
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
// 约束注解应用的时机
@Retention(RUNTIME)
@Documented
// 与约束注解关联的验证器
@Constraint(validatedBy = {NotEmptyValidator.class})
public @interface NotEmpty {
    // 约束注解验证时的输出消息
    String message() default "this string may be empty";
    // 约束注解在验证时所属的组别
    Class<?>[] groups() default { };
    // 约束注解的有效负载
    Class<? extends Payload>[] payload() default {};
}