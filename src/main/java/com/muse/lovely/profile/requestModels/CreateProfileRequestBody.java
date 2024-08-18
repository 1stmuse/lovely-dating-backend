package com.muse.lovely.profile.requestModels;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
public class CreateProfileRequestBody {

    private String firstname;
    private String lastname;
    private Date dateOfBirth;
    private String profilePicture;
    private String occupation;

}
