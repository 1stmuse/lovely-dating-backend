package com.muse.lovely.auth;

import lombok.*;


@Builder
@Getter
@Setter
public class RegisterUserRequest {

    private String email;
    private String password;
}
