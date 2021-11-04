package com.user.model;

import com.user.validation.ValidationGroup;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Objects;

@Entity
@DynamicUpdate
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null(groups = ValidationGroup.OnCreate.class, message = "{user.id.null}")
    @NotNull(groups = ValidationGroup.OnUpdate.class, message = "{user.id.not.null}")
    private Long id;

    @NotEmpty(message = "{user.first.name.not.empty}")
    private String firstName;

    @NotEmpty(message = "{user.last.name.not.empty}")
    private String lastName;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(Long id, String firstName, String lastName) {
        this(firstName, lastName);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId().equals(user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public void populate(User user) {

        this.firstName = user.getFirstName();

        this.lastName = user.getLastName();
    }
}
