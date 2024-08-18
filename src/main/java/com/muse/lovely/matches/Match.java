package com.muse.lovely.matches;

import com.muse.lovely.profile.Profile;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
public class Match {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "ownerId", nullable = false)
    private Profile owner;
    private Profile match;


    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
