package com.muse.lovely.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Builder
@Getter
@Setter
public class VerifyPhoneOtpRequest {


    @NotEmpty(message = "phone number should not be empty")
    private String phone;
    @NotEmpty(message = "otp should not be empty")
    private String otp;
}
