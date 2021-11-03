package com.user.repository;

import com.user.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void givenQuery_whenRequestToFindUsersByFullNameLike_shouldReturnThreeResults() {
        //given
        String query = "%Do%";
        //when
        Collection<User> users = this.userRepository.findUsersByFullNameLike(query);
        //then
        Assertions.assertThat(users.size()).isEqualTo(2);
    }

    @Test
    public void givenQuery_whenRequestToFindUsersByFirstNameLike_shouldReturnOneResult() {
        //given
        String query = "%Do%";
        //when
        Collection<User> users = this.userRepository.findUsersByFirstNameLike(query);
        //then
        Assertions.assertThat(users.size()).isEqualTo(1);
    }


    @Test
    public void givenQuery_whenRequestToFindUsersByLastNameLike_shouldReturnTwoResult() {
        //given
        String query = "%Do%";
        //when
        Collection<User> users = this.userRepository.findUsersByLastNameLike(query);
        //then
        Assertions.assertThat(users.size()).isEqualTo(2);
    }

}
