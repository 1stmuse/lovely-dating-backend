package com.muse.lovely.profile;


import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    public Profile findProfileByEmail(String email);
}
