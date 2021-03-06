package org.springframework.mvc.annotation;

import org.springframework.web.bind.RequestMehtod;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {

    String value() default "";

    RequestMehtod method() default RequestMehtod.GET;


}
