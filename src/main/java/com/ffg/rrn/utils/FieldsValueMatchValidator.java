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
public class FieldsValueMatchValidator implements ConstraintValidator<FieldsValueMatch, Object> {

	private String field;
	private String fieldMatch;
	private String message;

	@Override
	public void initialize(final FieldsValueMatch constraintAnnotation) {
		field = constraintAnnotation.field();
		fieldMatch = constraintAnnotation.fieldMatch();
		message = constraintAnnotation.message();
	}

	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext context) {
		boolean valid = true;
		try {
			final Object firstObj = new BeanWrapperImpl(value).getPropertyValue(field);
			final Object secondObj = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);

			valid = firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
		} catch (final Exception ignore) {
			// ignore
		}

		if (!valid) {
			context.buildConstraintViolationWithTemplate(message).addPropertyNode(field).addConstraintViolation().disableDefaultConstraintViolation();
		}

		return valid;
	}
}
