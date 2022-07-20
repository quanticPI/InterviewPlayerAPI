package com.player.task.entities;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class PlayerValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Player.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "name", "name.epmty");
		ValidationUtils.rejectIfEmpty(errors, "country", "country.epmty");
	}

}
