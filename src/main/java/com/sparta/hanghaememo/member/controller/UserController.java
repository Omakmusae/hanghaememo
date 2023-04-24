package com.sparta.hanghaememo.member.controller;


import com.sparta.hanghaememo.member.dto.LoginRequestDto;
import com.sparta.hanghaememo.member.dto.SignupRequestDto;
import com.sparta.hanghaememo.member.service.UserService;
import com.sparta.hanghaememo.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Controller//html만 반환하니 Controller 처리
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @ResponseBody
    @PostMapping("/signup")//회원가입
    public Message signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);

    }

    @ResponseBody
    @PostMapping("/login")
    public Message login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);

    }
}
