package com.user.validation;

import com.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserExistValidator extends UserValidator implements ConstraintValidator<UserExist, Object> {

    @Autowired
    protected UserService userService;

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {

        return this.getUser(o).isPresent();
    }

}
