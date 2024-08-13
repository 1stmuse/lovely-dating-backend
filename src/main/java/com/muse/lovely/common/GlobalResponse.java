package com.muse.lovely.common;

import lombok.*;

import java.util.Optional;

@Builder
@Getter
@Setter
public class GlobalResponse<T> {

    private Optional<T> data;
    private Optional<?> error = null;
    private int status;
    private String message;
}
