package com.sparta.hanghaememo.controller;


import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemoController {
    private final MemoService memoService; //controller와 서비스 연결

    @GetMapping("/")//메인 페이지
    public ModelAndView home() {
        return new ModelAndView("index"); //ModelAndView 객체에 index html을 반환
    }
    @PostMapping("/api/memos") // post 방식은 body에 넣어서 데이터를 전송해야함
    public Memo createMemo(@RequestBody MemoRequestDto requestDto) {
        return memoService.createMemo(requestDto);
    }
    @GetMapping("/api/memos")
    public List<Memo> getMemos() {
        return memoService.getMemos();
    }
    @PutMapping("/api/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        System.out.println("수정");
        return memoService.update(id, requestDto);
    }


}
