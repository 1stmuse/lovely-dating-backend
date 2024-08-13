package com.muse.lovely.profile;

import com.muse.lovely.gallery.Gallery;
import com.muse.lovely.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;


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
    private LocalDateTime dateOfBirth;
    private String gender;
    private String occupation;

    @OneToOne
    private User user;

    @OneToOne
    private Gallery gallery;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public String getFullName(){
        return firstname + " " + lastname;
    }

}
