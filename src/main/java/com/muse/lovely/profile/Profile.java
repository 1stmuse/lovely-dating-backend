package com.muse.lovely.profile;

import com.muse.lovely.gallery.Gallery;
import com.muse.lovely.likes.Like;
import com.muse.lovely.matches.Match;
import com.muse.lovely.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue
    private Long id;
    private String firstname;
    private String lastname;
    private Date dateOfBirth;
    private String gender;
    private String occupation;
    private String email;
    private String profilePicture;
    private List<String> interests;

    @OneToOne
    private User user;

    @OneToOne
    private Gallery gallery;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "location")
    private Geometry location;

    @OneToMany(mappedBy = "liker")
    private Set<Like> likesGiven;

    @OneToMany(mappedBy = "likedProfile")
    private Set<Like> likesReceived;

    @OneToMany(mappedBy = "owner")
    private Set<Match> matches;



    public String getFullName(){
        return firstname + " " + lastname;
    }

}
