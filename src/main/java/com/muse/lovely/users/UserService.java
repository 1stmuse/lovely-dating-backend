package com.muse.lovely.users;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByEmail(String email)  {
        return userRepository.findByEmail(email).orElseThrow();
    }
}
