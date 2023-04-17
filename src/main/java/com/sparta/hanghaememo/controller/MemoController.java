package com.sparta.hanghaememo.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class MemoController {
    @GetMapping("/")//메인 페이지
    public ModelAndView home() {
        return new ModelAndView("index"); //ModelAndView 객체에 index html을 반환
    }
}
