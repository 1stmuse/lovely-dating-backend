package com.muse.lovely.auth;

import lombok.*;

@Builder
@Getter
@Setter
public class VerifyEmailOtpRequest {

    private String email;
    private String otp;
}
