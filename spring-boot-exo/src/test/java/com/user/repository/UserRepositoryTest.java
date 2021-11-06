package com.user.repository;

import com.user.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@SpringBootTest
//rollback par défaut en fin de test
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void givenQuery_whenCallToFindUsersByFullNameLike_shouldReturnThreeResults() {
        //given
        String query = "%Do%";
        //when
        Collection<User> users = this.userRepository.findUsersByFullNameLike(query);
        //then
        Assertions.assertThat(users.size()).isEqualTo(3);
    }

    @Test
    public void givenQuery_whenCallToFindUsersByFirstNameLike_shouldReturnOneResult() {
        //given
        String query = "%Do%";
        //when
        Collection<User> users = this.userRepository.findUsersByFirstNameLike(query);
        //then
        Assertions.assertThat(users.size()).isEqualTo(1);
    }

    @Test
    public void givenQuery_whenCallToFindUsersByLastNameLike_shouldReturnTwoResult() {
        //given
        String query = "%Do%";
        //when
        Collection<User> users = this.userRepository.findUsersByLastNameLike(query);
        //then
        Assertions.assertThat(users.size()).isEqualTo(2);
    }

    @Test
    public void givenNotPersistedUser_whenCallSave_shouldReturnPersistedUserWithId() {
        //given
        User user = new User("Paul", "Leroy");
        //when
        user = this.userRepository.save(user);
        //then
        Assertions.assertThat(user.getId()).isGreaterThanOrEqualTo(0);
    }

    @Test
    public void givenPersistedUser_whenCallSave_shouldReturnUpdatedUser() throws Exception {
        //given
        Long id = 1l;

        Optional<User> oUser = this.userRepository.findById(id);

        if (oUser.isEmpty()) {
            throw new Exception("Unknown user id ");
        }

        //when
        User user = oUser.get();

        user.setLastName("Soldier");

        user.setFirstName("Unknown");

        User updatedUser = this.userRepository.save(user);

        //then
        Assertions.assertThat(user.getId()).isEqualTo(updatedUser.getId());
        Assertions.assertThat(user.getFirstName()).isEqualTo(updatedUser.getFirstName());
        Assertions.assertThat(user.getLastName()).isEqualTo(updatedUser.getLastName());
    }

    @Test
    public void givenPersistedUser_whenCallDeleteThenFind_shouldReturnEmptyUser() throws Exception {
        //given
        Long id = 1l;

        Optional<User> oUser = this.userRepository.findById(id);

        if (oUser.isEmpty()) {
            throw new Exception("Unknown user id ");
        }

        //when
        User user = oUser.get();

        this.userRepository.delete(user);

        oUser = this.userRepository.findById(id);

        //then
        Assertions.assertThat(oUser.isEmpty()).isTrue();
    }

}
