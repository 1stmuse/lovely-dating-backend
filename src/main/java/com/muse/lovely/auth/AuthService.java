package com.muse.lovely.auth;

import com.muse.lovely.WebsecurityConfig.JwtService;
import com.muse.lovely.common.GlobalResponse;
import com.muse.lovely.config.RedisService;
import com.muse.lovely.mailSender.MailService;
import com.muse.lovely.users.User;
import com.muse.lovely.users.UserRepository;
import com.muse.lovely.utils.Util;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final RedisService redisService;
    private final MailService mailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public void requestOtp(String email) throws MessagingException {
        String otp =  Util.generateOtp(6);
        redisService.saveItem(email + "otp", otp);
        mailService.sendMail("Lovely Email Verification", email.toString(), otp);
    }

    public ResponseEntity<GlobalResponse> verifyEmailOtp(VerifyEmailOtpRequest request) throws BadRequestException {
        Object savedOtp = redisService.getItem(request.getEmail() + "otp");
        if(savedOtp == null || !savedOtp.equals(request.getOtp())){
            throw new BadRequestException("Invalid or expired otp");
        }

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );

        user.setVerified(true);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(
                GlobalResponse.builder()
                        .message("Email Verified")
                        .status(HttpStatus.OK.value())
                        .build()
        );
    }

    public ResponseEntity<GlobalResponse> register(RegisterUserRequest request) throws BadRequestException, MessagingException {

        var user = userRepository.findByEmail(request.getEmail());
        if(user.isPresent()){
            throw new BadRequestException("User with this email already exist");
        }

        var newUser = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(newUser);
        var otp = Util.generateOtp(6);
        mailService.sendMail("Lovely Email Verification", request.getEmail(), otp);
        redisService.saveItem(request.getEmail() + "otp", otp);

        return ResponseEntity.ok(
                GlobalResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("User created please verify email, an OTP has been sent to your email")
                        .build()
        );
    }

    public ResponseEntity<GlobalResponse> authenticate(RegisterUserRequest request) throws BadRequestException {

        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var user = (User) auth.getPrincipal();
        var token = jwtService.generateToken(user);

        return ResponseEntity.ok(
                GlobalResponse.builder()
                        .status(HttpStatus.OK.value())
                        .data(Optional.of(token))
                        .message("login successful")
                        .build()
        );
    }
}
