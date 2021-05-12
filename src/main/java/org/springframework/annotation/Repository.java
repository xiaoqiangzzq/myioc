package org.springframework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类说明
 *
 * @author Zeng zhiqiang
 * @version V1.0 创建时间: 2021/5/12 16:44
 * Copyright 2021 by WiteMedia
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Repository {

    String value() default "";
}
