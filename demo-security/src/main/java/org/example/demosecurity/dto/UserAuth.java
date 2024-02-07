package org.example.demosecurity.dto;

import lombok.Getter;

@Getter
public class UserAuth {

    private final String userId;

    public UserAuth(String userId) {
        this.userId = userId;
    }
}
