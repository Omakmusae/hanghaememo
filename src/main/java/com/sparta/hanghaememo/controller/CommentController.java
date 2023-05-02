package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.CommentRequestDto;
import com.sparta.hanghaememo.dto.CommentResponseDto;
import com.sparta.hanghaememo.message.Message;
import com.sparta.hanghaememo.security.UserDetailsImpl;
import com.sparta.hanghaememo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(commentRequestDto, userDetails.getUser());
    }

    // 댓글 수정
    @PutMapping("/{memo_id}")
    public CommentResponseDto updateComment(@PathVariable Long memo_id, @RequestBody CommentRequestDto commentRequestDto,  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(memo_id, commentRequestDto, userDetails.getUser());
    }

    // 댓글 삭제
    @DeleteMapping("/{memo_id}")
    public Message deleteComment(@PathVariable Long memo_id,  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(memo_id, userDetails.getUser());
    }
}
