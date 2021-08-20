package com.bernardawj.notey.dto.auth;

import com.bernardawj.notey.dto.user.UserDTO;

public class AuthDTO {

    private UserDTO user;
    private TokenDTO token;

    public AuthDTO() {
    }

    public AuthDTO(UserDTO user, TokenDTO token) {
        this.user = user;
        this.token = token;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public TokenDTO getToken() {
        return token;
    }

    public void setToken(TokenDTO token) {
        this.token = token;
    }
}
