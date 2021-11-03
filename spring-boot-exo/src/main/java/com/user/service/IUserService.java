package com.user.service;

import com.user.model.User;

import java.util.Collection;
import java.util.Optional;

public interface IUserService {

    Collection<User> findUsersByFirstNameLike(String query);

    Collection<User> findUsersByLastNameLike(String query);

    Collection<User> findUsersByFullNameLike(String query);

    Optional<User> findById(Long id);

    User saveUser(User user);

    void deleteUser(Long id);
}
