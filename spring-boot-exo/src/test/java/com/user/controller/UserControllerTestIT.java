package com.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.model.User;
import com.user.validation.ValidationErrorResponse;
import com.user.validation.Violation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    //pour comparaison des messages reçus en réponse
    private MessageSource messageSource;

    /**
     * Test si un utilisateur sérialisé au format json et transmis au endpoint createUser,
     * est bien persisté.
     */
    @Test
    public void givenValidSerializedUser_whenRequestToCreateUserPostEndPoint_shouldReturnPersistedUser() throws Exception {

        //GIVEN
        User user = new User("Jean", "Dupond");

        String serializedUser = objectMapper.writeValueAsString(user);

        //WHEN
        MvcResult result = this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(serializedUser))
                .andExpect(status().isOk())
                .andReturn();

        //THEN
        serializedUser = result.getResponse().getContentAsString();

        User newUser = objectMapper.readValue(serializedUser, User.class);

        Assertions.assertThat(newUser.getId()).isGreaterThanOrEqualTo(0);
        Assertions.assertThat(newUser.getFirstName()).isEqualTo(user.getFirstName());
        Assertions.assertThat(newUser.getLastName()).isEqualTo(user.getLastName());
    }

    /**
     * Test si un utilisateur sérialisé au format json et transmis au endpoint createUser
     * avec un attribut firstname vide provoque bien une erreur 400 BadRequest
     */
    @Test
    public void givenInvalidSerializedUserWithMissingFirstname_whenRequestToCreateUserPostEndPoint_shouldReturnBadRequest() throws Exception {

        //GIVEN
        String serializedUser = objectMapper.writeValueAsString(new User("Jean", ""));

        //WHEN THEN
        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(serializedUser))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    /**
     * Test si un utilisateur sérialisé au format json et transmis au endpoint createUser
     * avec un attribut lastname vide provoque bien une erreur 400 BadRequest
     */
    @Test
    public void givenInvalidSerializedUserWithMissingLastname_whenRequestToCreateUserPostEndPoint_shouldReturnBadRequest() throws Exception {

        //GIVEN
        String serializedUser = objectMapper.writeValueAsString(new User("", "Dupond"));

        //WHEN THEN
        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(serializedUser))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    /**
     * Test si un utilisateur sérialisé au format json et transmis au endpoint updateUser
     * est bien mis à jour.
     */
    @Test
    public void givenValidSerializedUser_whenRequestToUpdateUserPutEndPoint_shouldReturnOk() throws Exception {

        //GIVEN

        User user = new User(1l, "Albert", "Dubois");
        String serializedUser = objectMapper.writeValueAsString(user);
        //WHEN THEN
        MvcResult result = this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(serializedUser))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        User updatedUser = objectMapper.readValue(response, User.class);

        Assertions.assertThat(updatedUser.getFirstName()).isEqualTo(user.getFirstName());

        Assertions.assertThat(updatedUser.getLastName()).isEqualTo(user.getLastName());

        Assertions.assertThat(updatedUser.getId()).isEqualTo(user.getId());
    }

    /**
     * Test si un utilisateur sérialisé au format json et transmis au endpoint updateUser
     * avec un prenom vide retourne bien une erreur 400 BadRequest avec le message correspondant.
     */
    @Test
    public void givenInvalidSerializedUserWithEmptyFirstname_whenRequestToUpdateUserPutEndPoint_shouldReturnBadRequest() throws Exception {

        //GIVEN
        Long id = 1l;
        String firstName = "";
        String lastName = "Dubois";
        User user = new User(id, firstName, lastName);
        String serializedUser = objectMapper.writeValueAsString(user);
        //WHEN THEN
        MvcResult result = this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(serializedUser))
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        ValidationErrorResponse validationErrorResponse = objectMapper.readValue(response, ValidationErrorResponse.class);

        Violation violation = validationErrorResponse.getViolations().get(0);

        Assertions.assertThat(violation.getStatus()).isEqualTo(400);

        Assertions.assertThat(violation.getMessage()).isEqualTo(
                this.messageSource.getMessage("user.first.name.not.empty", null, null));
    }

    /**
     * Test si un utilisateur sérialisé au format json et transmis au endpoint updateUser
     * avec un nom vide retourne bien une erreur 400 BadRequest avec le message correspondant.
     */
    @Test
    public void givenInvalidSerializedUserWithEmptyLastname_whenRequestToUpdateUserPutEndPoint_shouldReturnBadRequest() throws Exception {

        //GIVEN
        Long id = 1l;
        String firstName = "Jean";
        String lastName = "";
        User user = new User(id, firstName, lastName);
        String serializedUser = objectMapper.writeValueAsString(user);
        //WHEN THEN
        MvcResult result = this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(serializedUser))
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        ValidationErrorResponse validationErrorResponse = objectMapper.readValue(response, ValidationErrorResponse.class);

        Violation violation = validationErrorResponse.getViolations().get(0);

        Assertions.assertThat(violation.getStatus()).isEqualTo(400);

        Assertions.assertThat(violation.getMessage()).isEqualTo(
                this.messageSource.getMessage("user.last.name.not.empty", null, null));
    }

    /**
     * Test si un utilisateur sérialisé au format json et transmis au endpoint updateUser
     * avec le nom est le prenom manquant retourne bien une erreur 400 BadRequest avec les messages correspondant.
     */
    @Test
    public void givenInvalidSerializedUserWithEmptyFullName_whenRequestToUpdateUserPutEndPoint_shouldReturnBadRequest() throws Exception {

        //GIVEN
        User user = new User(1l, "", "");
        String serializedUser = objectMapper.writeValueAsString(user);
        //WHEN THEN
        MvcResult result = this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(serializedUser))
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        ValidationErrorResponse validationErrorResponse = objectMapper.readValue(response, ValidationErrorResponse.class);

        Assertions.assertThat(validationErrorResponse.getViolations())
                .extracting(Violation::getStatus).containsExactly(400, 400);

        Assertions.assertThat(validationErrorResponse.getViolations())
                .extracting(Violation::getMessage).hasSameElementsAs(
                        List.of(
                                this.messageSource.getMessage("user.last.name.not.empty", null, null),
                                this.messageSource.getMessage("user.first.name.not.empty", null, null)));
    }

    /**
     * Test si un utilisateur sérialisé au format json et transmis au endpoint updateUser
     * avec une id inconnue retourne bien une erreur 400 BadRequest avec le message correspondant.
     */
    @Test
    public void givenInvalidSerializedUserWithUnknownId_whenRequestToUpdateUserPutEndPoint_shouldReturnBadRequest() throws Exception {

        //GIVEN
        Long id = -1l;
        String firstName = "Jean";
        String lastName = "Dubois";
        User user = new User(id, firstName, lastName);
        String serializedUser = objectMapper.writeValueAsString(user);
        //WHEN THEN
        MvcResult result = this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(serializedUser))
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        ValidationErrorResponse validationErrorResponse = objectMapper.readValue(response, ValidationErrorResponse.class);

        Violation violation = validationErrorResponse.getViolations().get(0);

        Assertions.assertThat(violation.getStatus()).isEqualTo(404);

        Assertions.assertThat(violation.getMessage()).isEqualTo(
                this.messageSource.getMessage("user.not.found", null, null));
    }


    /**
     * Test si la suppression d'un utilisatuer à partir de son id retourne un reponse 200.
     */
    @Test
    public void givenUserValidId_whenRequestToDeleteUserEndPoint_shouldReturnOk() throws Exception {

        //GIVEN
        Long id = 1l;

        //WHEN THEN
        this.mockMvc.perform(
                        MockMvcRequestBuilders.delete("/users/id/" + id))
                .andExpect(status().isOk())
                .andReturn();
    }

    /**
     * Test si la suppression d'un utilisateur à partir d'une id inexistante retourne un reponse 400 BadRequest
     * avec un message associé à un code 404 NotFound.
     */
    @Test
    public void givenUserInvalidId_whenRequestToDeleteUserEndPoint_shouldReturnBadRequest() throws Exception {

        //GIVEN
        Long id = -1l;

        //WHEN THEN
        MvcResult result = this.mockMvc.perform(
                        MockMvcRequestBuilders.delete("/users/id/" + id))
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        ValidationErrorResponse validationErrorResponse = objectMapper.readValue(response, ValidationErrorResponse.class);

        Assertions.assertThat(validationErrorResponse.getViolations())
                .extracting(Violation::getStatus, Violation::getMessage)
                .containsExactly(Assertions.tuple(404,
                        this.messageSource.getMessage("user.not.found", null, null)));

    }
}
