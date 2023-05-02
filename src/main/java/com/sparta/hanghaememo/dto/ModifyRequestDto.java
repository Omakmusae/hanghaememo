package com.sparta.hanghaememo.dto;

import lombok.Getter;

@Getter
public class ModifyRequestDto {
    private String title;
    private String content;
    private int memoLike;
}