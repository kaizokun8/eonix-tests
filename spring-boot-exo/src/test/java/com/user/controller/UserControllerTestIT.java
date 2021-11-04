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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageSource messageSource;

    @Test
    public void givenValidSerializedUser_whenRequestToCreateUserPostEndPoint_shouldReturnPersistedUser() throws Exception {

        //GIVEN
        String serializedUser = objectMapper.writeValueAsString(new User("Jean", "Dupond"));

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
    }

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

    @Test
    public void givenValidSerializedUser_whenRequestToUpdateUserPutEndPoint_shouldReturnOk() throws Exception {

        //GIVEN
        Long id = 1l;
        String firstName = "Albert";
        String lastName = "Dubois";
        User user = new User(id, firstName, lastName);
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

        Assertions.assertThat(updatedUser.getFirstName()).isEqualTo(firstName);

        Assertions.assertThat(updatedUser.getLastName()).isEqualTo(lastName);

        Assertions.assertThat(updatedUser.getId()).isEqualTo(id);
    }

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

        Violation violation = validationErrorResponse.getViolations().get(0);

        Assertions.assertThat(violation.getStatus()).isEqualTo(404);

        Assertions.assertThat(violation.getMessage()).isEqualTo(
                this.messageSource.getMessage("user.not.found", null, null));
    }
}
