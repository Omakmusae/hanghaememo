package com.sparta.hanghaememo.dto;
import com.sparta.hanghaememo.entity.Memo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemoModifyDto {
    private String title;
    private String author;
    private String content;
    private LocalDateTime modifiedAt;

    public MemoModifyDto(Memo memo) {
        this.title = memo.getTitle();
        this.content = memo.getContent();
        this.author = memo.getAuthor();
        this.modifiedAt = memo.getModifiedAt();
    }
}
