package com.muse.lovely.common.errorHandler;

import com.muse.lovely.common.GlobalResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestControllerAdvice
public class ErrorResponseHandler  {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<GlobalResponse> handleResponse(
            BadRequestException e
    ){
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
               GlobalResponse.builder()
                       .data(null)
                       .status(HttpStatus.BAD_REQUEST.value())
                       .error(Optional.ofNullable(e.getMessage()))
                       .message(e.getMessage())
                       .build()
       );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponse> handleResponse(
            Exception e
    ){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                GlobalResponse.builder()
                        .data(null)
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .error(Optional.ofNullable(e.getMessage()))
                        .message("Server Error")
                        .build()
        );
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<GlobalResponse> handleException(
            MessagingException e
    ){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(
                        GlobalResponse.builder()
                                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .error(Optional.ofNullable(e.getMessage()))
                                .message(e.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<GlobalResponse> handleException(
            BadRequestException e
    ){
        return ResponseEntity.status(UNAUTHORIZED).body(
                GlobalResponse.builder()
                        .status(UNAUTHORIZED.value())
                        .error(Optional.ofNullable(e.getMessage()))
                        .message(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(AddressException.class)
    public ResponseEntity<GlobalResponse> handleException(
            AddressException e
    ){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                GlobalResponse.builder()
                        .status(INTERNAL_SERVER_ERROR.value())
                        .error(Optional.ofNullable(e.getMessage()))
                        .message(e.getMessage())
                        .build()
        );
    }
}
