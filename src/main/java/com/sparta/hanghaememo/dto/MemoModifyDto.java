package com.sparta.hanghaememo.dto;
import com.sparta.hanghaememo.entity.Memo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemoModifyDto {
    private String title;
    private String username;
    private String content;
    private LocalDateTime modifiedAt;

    public MemoModifyDto(Memo memo) {
        this.title = memo.getTitle();
        this.content = memo.getContent();
        this.username = memo.getUsername();
        this.modifiedAt = memo.getModifiedAt();
    }
}
