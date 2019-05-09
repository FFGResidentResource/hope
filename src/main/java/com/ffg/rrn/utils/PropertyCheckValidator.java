/**
 * 
 */
package com.ffg.rrn.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

/**
 * @author FFGRRNTeam
 *
 */
public class PropertyCheckValidator implements ConstraintValidator<PropertyCheck, Object> {

	private String field;
	private String fieldCheck;
	private String message;

	@Override
	public void initialize(final PropertyCheck constraintAnnotation) {
		field = constraintAnnotation.field();
		fieldCheck = constraintAnnotation.fieldCheck();
		message = constraintAnnotation.message();
	}

	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext context) {
		boolean valid = true;
		try {
			final Object firstObj = new BeanWrapperImpl(value).getPropertyValue(field);
			final Object secondObj = new BeanWrapperImpl(value).getPropertyValue(fieldCheck);

			// On Admin Tab - > Either Admin is checked and no property is selected OR admin
			// is not checked but some property is selected
			valid = ((Boolean) firstObj && ((Integer) secondObj == 0) || (!(Boolean) firstObj && ((Integer) secondObj > 0)));

		} catch (final Exception ignore) {
			// ignore
		}

		if (!valid) {
			context.buildConstraintViolationWithTemplate(message).addPropertyNode(field).addConstraintViolation().disableDefaultConstraintViolation();
		}

		return valid;
	}
}
