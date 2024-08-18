package com.muse.lovely.profile;

import com.muse.lovely.common.GlobalResponse;
import com.muse.lovely.profile.requestModels.CreateProfileRequestBody;
import com.muse.lovely.profile.requestModels.GenderRequest;
import com.muse.lovely.profile.requestModels.InterestsRequest;
import com.muse.lovely.profile.requestModels.UpdateLocationRequest;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.io.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("")
    public ResponseEntity<GlobalResponse> create(@RequestBody CreateProfileRequestBody requestBody, Authentication connectedUser)  {

        return profileService.createProfile(requestBody, connectedUser);
    }

    @PutMapping("/profile/location")
    public ResponseEntity<GlobalResponse> updateLocation(
            @RequestBody UpdateLocationRequest request,
            Authentication connectedUser
    ) throws ParseException {
        return profileService.updateLocation(request.getLat(), request.getLng(), connectedUser);
    }

    @PutMapping("/profile/interest")
    public ResponseEntity<GlobalResponse> updateInterest(@RequestBody InterestsRequest request, Authentication connectedUser){
        return profileService.updateInterest(request, connectedUser);
    }

    @PutMapping("/profile/gender")
    public ResponseEntity<GlobalResponse> updateGender(@RequestBody GenderRequest request, Authentication connectedUser){
        return profileService.updateGender(request, connectedUser);
    }

    @PostMapping("/profile/{profileId}/like")
    public ResponseEntity likeProfile(
            @PathVariable(name = "profileId") Long profileId,
            Authentication connectedUser
    ){
        return profileService.likeProfile(profileId, connectedUser);
    }









}
