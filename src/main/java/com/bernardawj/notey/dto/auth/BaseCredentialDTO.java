package com.bernardawj.notey.dto.auth;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public abstract class BaseCredentialDTO {

    @NotEmpty(message = "{auth.email.empty}")
    @Email(message = "{auth.email.invalid}")
    private String email;

    @NotEmpty(message = "{auth.password.empty}")
    @Length(min = 8, max = 16, message = "{auth.password.length}")
    private String password;

    public BaseCredentialDTO() {
    }

    public BaseCredentialDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
