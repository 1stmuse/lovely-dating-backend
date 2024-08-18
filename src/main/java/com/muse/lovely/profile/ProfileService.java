package com.muse.lovely.profile;

import com.muse.lovely.common.GlobalResponse;
import com.muse.lovely.likes.Like;
import com.muse.lovely.likes.LikeRepository;
import com.muse.lovely.profile.requestModels.CreateProfileRequestBody;
import com.muse.lovely.profile.requestModels.GenderRequest;
import com.muse.lovely.profile.requestModels.InterestsRequest;
import com.muse.lovely.users.User;
import com.muse.lovely.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    private User getConnectedUser(Authentication connectedUser){
        return (User) connectedUser.getPrincipal();
    }

    public ResponseEntity<GlobalResponse> createProfile(CreateProfileRequestBody requestBody, Authentication connectUser) {
        User user = getConnectedUser(connectUser);

        if(user == null){
            throw new BadCredentialsException("user not found");
        }

        Profile profile = Profile.builder()
                .dateOfBirth(requestBody.getDateOfBirth())
                .firstname(requestBody.getFirstname())
                .lastname(requestBody.getLastname())
                .occupation(requestBody.getOccupation())
                .profilePicture(requestBody.getProfilePicture())
                .email(user.getEmail())
                .build();

        profileRepository.save(profile);
        return ResponseEntity.ok(GlobalResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("Profile created successfully")
                        .data(Optional.of(profile))
                .build());
    }

    public ResponseEntity<GlobalResponse> updateLocation(String lat, String lng, Authentication connectedUser) throws ParseException {
        User user = getConnectedUser(connectedUser);

        if(user == null){
            throw new BadCredentialsException("user not found");
        }
        Profile profile = profileRepository.findProfileByEmail(user.getEmail());

        if(profile == null){
            throw new BadCredentialsException("profile not found");
        }

        WKTReader wtkReader = new WKTReader();
        Geometry geometry = wtkReader.read("POINT (+" + lat + " " + lng + " )");
        profile.setLocation(geometry);
        return ResponseEntity.ok(GlobalResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("location updated")
                .build());
    }

    public ResponseEntity<GlobalResponse> updateInterest(
            InterestsRequest request,
            Authentication connectedUser) {

        User user = getConnectedUser(connectedUser);

        if(user == null){
            throw new BadCredentialsException("user not found");
        }
        Profile profile = profileRepository.findProfileByEmail(user.getEmail());

        if(profile == null){
            throw new BadCredentialsException("profile not found");
        }

        profile.setInterests(request.interest());
        profileRepository.save(profile);

        return ResponseEntity.ok(GlobalResponse.builder()
                .status(HttpStatus.OK.value())
                .message("interest updated")
                .build());
    }

    public ResponseEntity<GlobalResponse> updateGender(GenderRequest request, Authentication connectedUser) {
        User user = getConnectedUser(connectedUser);

        if(user == null){
            throw new BadCredentialsException("user not found");
        }
        Profile profile = profileRepository.findProfileByEmail(user.getEmail());

        if(profile == null){
            throw new BadCredentialsException("profile not found");
        }

        profile.setGender(request.gender());
        profileRepository.save(profile);
        return ResponseEntity.ok(GlobalResponse.builder()
                .status(HttpStatus.OK.value())
                .message("gender updated")
                .build());
    }

    public ResponseEntity likeProfile(Long profileId, Authentication connectedUser) {
        var user = getConnectedUser(connectedUser);

        var myProfile = profileRepository.findProfileByEmail(user.getEmail());
        var likedProfile = profileRepository.findById(profileId).get();

        var like = Like.builder()
                .likedProfile(likedProfile)
                .liker(myProfile)
                .build();

        var myLikes = myProfile.getLikesGiven();
        myLikes.add(like);
        myProfile.setLikesGiven(myLikes);

        profileRepository.save(myProfile);
        likeRepository.save(like);

        return ResponseEntity.accepted().build();
    }
}
