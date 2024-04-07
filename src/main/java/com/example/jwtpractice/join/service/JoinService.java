package com.example.jwtpractice.join.service;

import com.example.jwtpractice.common.entity.UserEntity;
import com.example.jwtpractice.common.repository.UserRepository;
import com.example.jwtpractice.join.dto.JoinDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDto joinDto) {

        String username = joinDto.getUsername();
        String password = joinDto.getPassword();

        Boolean isExist = userRepository.existsByUsername(username);

        if (isExist) {

            return;
        }
        String encodedPassword = bCryptPasswordEncoder.encode(password);

        UserEntity data = new UserEntity(username, encodedPassword, "ROLE_ADMIN");


        userRepository.save(data);
    }
}
