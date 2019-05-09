/**
 * 
 */
package com.ffg.rrn.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author FFGRRNTeam
 *
 */
@Constraint(validatedBy = FieldsValueMatchValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldsValueMatch {
 
    String message() default "Fields values don't match!";
 
    String field();
 
	String fieldMatch();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
 
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        FieldsValueMatch[] value();
    }
}