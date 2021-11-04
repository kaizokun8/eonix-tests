package com.user.validation;

import com.user.model.User;
import com.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserValidator {

    @Autowired
    protected UserService userService;

    protected Optional<User> getUser(Object o) {

        if (o == null) {
            return Optional.empty();
        }

        Long userId = -1l;

        if (o instanceof User) {

            userId = ((User) o).getId();

        } else if (o instanceof Long) {

            userId = (Long) o;
        }

        return this.userService.findById(userId);
    }
}
