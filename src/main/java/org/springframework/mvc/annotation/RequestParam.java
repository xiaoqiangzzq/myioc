package org.springframework.mvc.annotation;


import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParam {

    String name() default "";

    boolean required() default  false;
}
