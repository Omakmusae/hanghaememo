package com.sparta.hanghaememo.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long id;
    private String content;
    //private String username;
    private Long memo_id;
}
