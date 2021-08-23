package com.bernardawj.notey.dto.auth;

import javax.validation.constraints.NotEmpty;

public class RegisterDTO extends BaseCredentialDTO {

    @NotEmpty(message = "{user.firstName.empty}")
    private String firstName;

    @NotEmpty(message = "{user.lastName.empty}")
    private String lastName;

    public RegisterDTO(String email, String password, String firstName, String lastName) {
        super(email, password);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
