package com.muse.lovely.auth;

import com.muse.lovely.common.GlobalResponse;
import com.muse.lovely.users.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/otp-email")
    public ResponseEntity requestEmailOtp(
            @RequestBody RequestOtpBody request
    ) throws MessagingException {
        authService.requestOtp(request.email());
        return ResponseEntity.accepted().build();

    }

    @PostMapping("/otp-phone")
    public ResponseEntity requestPhoneOtp(
            @RequestBody String phone
    ) throws MessagingException {
        authService.requestOtp(phone);

        return ResponseEntity.accepted().build();

    }

    @PostMapping("/verify-otp-email")
    public ResponseEntity<GlobalResponse> verifyEmailOtp(
            @RequestBody VerifyEmailOtpRequest request
    ) throws BadRequestException {
         return authService.verifyEmailOtp(request);
    }

    @PostMapping("/verify-otp-phone")
    public ResponseEntity verifyPhoneOtp(
            @RequestBody VerifyPhoneOtpRequest request
    ){
        return  ResponseEntity.accepted().build();
    }

    @PostMapping("/register")
    public ResponseEntity<GlobalResponse> register(
            @RequestBody RegisterUserRequest request
    ) throws BadRequestException, MessagingException {

        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<GlobalResponse> login(
            @RequestBody RegisterUserRequest request
    ) throws BadRequestException {

        return authService.authenticate(request);
    }
}
