package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.MemoModifyDto;
import com.sparta.hanghaememo.dto.MemoResponseDto;
import com.sparta.hanghaememo.dto.MemoRequestDto;

import com.sparta.hanghaememo.dto.ModifyRequestDto;
import com.sparta.hanghaememo.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemoController {
    private final MemoService memoService; //controller와 서비스 연결

    @GetMapping("/")//메인 페이지
    public ModelAndView home() {
        return new ModelAndView("index"); //ModelAndView 객체에 index html을 반환
    }

    //CRUD
    @PostMapping("/post") // post 방식은 body에 넣어서 데이터를 전송해야함
    public MemoResponseDto createMemo(@RequestBody ModifyRequestDto modifyRequestDto, HttpServletRequest request) {
        return memoService.createMemo(modifyRequestDto, request);
    }

    @GetMapping("/posts") // 전체 메모 조회
    public ResponseEntity<Map<String, List<MemoResponseDto>>> Memos() {
        return memoService.getMemos();
    }

    @GetMapping("/post/{id}") // 선택 메모 조회
    public MemoResponseDto selectMemo(@PathVariable Long id) {
        return memoService.selectMemo(id);
    }

    @PutMapping("/post/{id}")
    public MemoModifyDto updateMemo(@PathVariable Long id, @RequestBody ModifyRequestDto modifyRequestDto, HttpServletRequest request) throws Exception{
        System.out.println("수정");
        return memoService.update(id, modifyRequestDto, request);
    }

    @DeleteMapping("/post/{id}")
    public String deleteMemo(@PathVariable Long id, HttpServletRequest request) throws Exception{
        return memoService.deleteMemo(id, request);
    }


}