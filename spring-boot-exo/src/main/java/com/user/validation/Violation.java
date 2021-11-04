package com.user.validation;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Violation {

    private  String fieldName;

    private  String message;

    private int status;

    public Violation(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

}
