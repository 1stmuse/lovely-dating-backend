package com.muse.lovely.likes;

import com.muse.lovely.profile.Profile;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
public class Like {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "likerId", nullable = false)
    private Profile liker;
    @ManyToOne
    @JoinColumn(name = "likedProfileId", nullable = false)
    private Profile likedProfile;


    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
