package com.java.utilities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthPayLoad {
	
    @JsonProperty
    private String username;
    @JsonProperty
    private String password;

    public AuthPayLoad (String username, String password) {
        this.username = username;
        this.password = password;
    }

}
