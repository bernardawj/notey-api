package com.bernardawj.notey.dto.auth;

import java.util.Date;

public class TokenDTO {

    private String accessToken;
    private Date expiryDate;

    public TokenDTO() {
    }

    public TokenDTO(String accessToken, Date expiryDate) {
        this.accessToken = accessToken;
        this.expiryDate = expiryDate;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
