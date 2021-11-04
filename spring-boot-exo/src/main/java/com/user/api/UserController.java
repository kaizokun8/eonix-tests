package com.user.api;

import com.user.model.User;
import com.user.validation.UserExist;
import com.user.validation.ValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.user.service.IUserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@RestController
@Validated
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping(value = {"/users/full-name/{query}", "/users/full-name"})
    public ResponseEntity<?> getUsersbyFullName(@PathVariable(required = false) String query) {

        query = query == null ? "" : query;

        Collection<User> usersLike = this.userService.findUsersByFullName(query);

        return new ResponseEntity<>(usersLike, HttpStatus.OK);
    }

    @GetMapping(value = {"/users/first-name/{query}", "/users/first-name"})
    public ResponseEntity<?> getUsersByFirstName(@PathVariable(required = false) String query) {

        query = query == null ? "" : query;

        Collection<User> usersLike = this.userService.findUsersByFirstName(query);

        return new ResponseEntity<>(usersLike, HttpStatus.OK);
    }

    @GetMapping(value = {"/users/last-name/{query}", "/users/last-name"})
    public ResponseEntity<?> getUsersByLastName(@PathVariable(required = false) String query) {

        query = query == null ? "" : query;

        Collection<User> usersLike = this.userService.findUsersByLastName(query);

        return new ResponseEntity<>(usersLike, HttpStatus.OK);
    }

    @GetMapping(value = "/users/id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable @UserExist Long id) {

        User user = this.userService.findById(id).get();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = {"/users"})
    @Validated(ValidationGroup.OnCreate.class)
    public ResponseEntity<?> createUser(@Valid
                                        @NotNull
                                        @RequestBody User user) {

        user = this.userService.saveUser(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping(value = {"/users"})
    @Validated(ValidationGroup.OnUpdate.class)
    public ResponseEntity<?> updateUser(@Valid
                                        @NotNull @UserExist(groups = ValidationGroup.OnUpdate.class)
                                        @RequestBody User user_p) {

        User user = this.userService.findById(user_p.getId()).get();

        user.populate(user_p);

        user = this.userService.saveUser(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping(value = {"/users/id/{id}"})
    public ResponseEntity<?> deleteUser(@NotNull @UserExist
                                        @PathVariable Long id) {

        this.userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
