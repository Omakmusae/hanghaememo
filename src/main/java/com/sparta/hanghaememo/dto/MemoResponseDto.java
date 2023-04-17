package com.sparta.hanghaememo.dto;
import lombok.Getter;

@Getter
public class MemoResponseDto {
    private String title;
    private String content;
    private String author;

    public MemoResponseDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
