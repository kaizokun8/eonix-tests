package com.user.service;

import com.user.model.User;

import java.util.Collection;
import java.util.Optional;

public interface IUserService {

    Collection<User> findUsersByFirstName(String query);

    Collection<User> findUsersByLastName(String query);

    Collection<User> findUsersByFullName(String query);

    Optional<User> findById(Long id);

    User saveUser(User user);

    void deleteUser(Long id);
}
