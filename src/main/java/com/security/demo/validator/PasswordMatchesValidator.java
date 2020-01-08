package com.security.demo.validator;

import com.security.demo.dto.RegistrationDto;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author 李羅
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

	@Override
	public void initialize(PasswordMatches constraintAnnotation) {
	}

	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		RegistrationDto registration = (RegistrationDto) obj;
		return registration.getPassword().equals(registration.getMatchingPassword());
	}
}
