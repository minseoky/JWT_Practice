package com.example.jwtpractice.join.controller;

import com.example.jwtpractice.join.dto.JoinDto;
import com.example.jwtpractice.join.service.JoinService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {

        this.joinService = joinService;
    }

    @PostMapping("/join")
    public String joinProcess(@RequestBody JoinDto joinDto) {

        joinService.joinProcess(joinDto);

        return "ok";
    }
}
