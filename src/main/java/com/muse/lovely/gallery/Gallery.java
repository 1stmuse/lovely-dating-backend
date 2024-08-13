package com.muse.lovely.gallery;


import com.muse.lovely.profile.Profile;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
public class Gallery {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(mappedBy = "gallery")
    private Profile owner;
    private List<String> images;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
