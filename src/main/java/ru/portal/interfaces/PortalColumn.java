/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.portal.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Igor Salnikov
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(
        {ElementType.FIELD, ElementType.METHOD})
public @interface PortalColumn {

    String direction() default "NONE";

    int displayOrder() default 999;

    int sortOrder() default 999;

    boolean sortable() default true;

    String renderer() default "";

    String field() default "";

    int width() default 0;
}
