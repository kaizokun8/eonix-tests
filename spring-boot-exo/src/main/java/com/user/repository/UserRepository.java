package com.user.repository;

import com.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where lower(u.firstName) like concat('%',lower(:query),'%')" +
            " or lower(u.lastName) like concat('%',lower(:query),'%')")
    Collection<User> findUsersByFullNameLike(@Param("query") String query);

    @Query("select u from User u where lower(u.firstName) like concat('%',lower(:query),'%')")
    Collection<User> findUsersByFirstNameLike(@Param("query") String query);

    @Query("select u from User u where lower(u.lastName) like concat('%',lower(:query),'%')")
    Collection<User> findUsersByLastNameLike(@Param("query") String query);
}
