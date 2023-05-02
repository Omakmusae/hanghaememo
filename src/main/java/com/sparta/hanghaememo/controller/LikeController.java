package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.message.Message;
import com.sparta.hanghaememo.security.UserDetailsImpl;
import com.sparta.hanghaememo.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class LikeController {

    private final LikeService likeService;

    // Memo 게시물에 좋아요
    @PostMapping("/memo/{memo_id}")
    public Message postLike(@PathVariable Long memo_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.memoLike(memo_id, userDetails.getUser());
    }

    // Comment 댓글에 좋아요
    @PostMapping("/comment/{comment_id}")
    public Message commentLike(@PathVariable Long comment_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.commentLike(comment_id, userDetails.getUser());
    }
}
