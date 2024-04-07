package com.example.jwtpractice.authentication.service;

import com.example.jwtpractice.authentication.dto.CustomUserDetails;
import com.example.jwtpractice.common.entity.UserEntity;
import com.example.jwtpractice.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByUsername(username);

        if(user != null) {
            //UserDetails에 담아서 반환하면 AuthenticationManager가 검증
            return new CustomUserDetails(user);
        }

        return null;
    }
}
