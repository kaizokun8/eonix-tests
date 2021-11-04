package com.user.service;

import com.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import com.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Collection<User> findUsersByFirstName(String query) {

        return this.userRepository.findUsersByFirstNameLike(query);
    }

    @Override
    public Collection<User> findUsersByLastName(String query) {

        return this.userRepository.findUsersByLastNameLike(query);
    }

    @Override
    public Collection<User> findUsersByFullName(String query) {

        return this.userRepository.findUsersByFullNameLike(query);
    }

    @Override
    public Optional<User> findById(Long id) {

        return this.userRepository.findById(id);
    }

    @Override
    public User saveUser(User user) {

        user = this.userRepository.save(user);

        return user;
    }

    @Override
    public void deleteUser(Long id) {

        this.userRepository.deleteById(id);
    }
}
