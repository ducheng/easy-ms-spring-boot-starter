package com.ducheng.easy.ms.anotation;

import java.lang.annotation.*;

/**
 *  自定义注解
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomIndex {

    String uid() default "";

    String primaryKey() default "";
}
