package com.user.api;

import com.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.user.service.IUserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
@Validated
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping(value = "/users/full-name/like/{query}")
    public ResponseEntity<?> getUsersLike(@PathVariable(required = false) String query) {

        Collection<User> usersLike = this.userService.findUsersByFullNameLike(query);

        return new ResponseEntity<>(usersLike, HttpStatus.OK);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {

        Optional<User> user = this.userService.findById(id);

        if (user.isPresent()) {

            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = {"/users"})
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {

        user = this.userService.saveUser(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping(value = {"/users"})
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user) {

        user = this.userService.saveUser(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping(value = {"/users/{id}"})
    public ResponseEntity<?> deleteUser(@Valid @PathVariable Long id) {

        this.userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
