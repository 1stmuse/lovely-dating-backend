package com.muse.lovely.profile;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
public class UpdateProfileRequestBody {

    private String gender;
    private List<String> interest;
    private String address;
    private String bio;
}
