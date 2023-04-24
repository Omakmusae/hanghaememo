package com.sparta.hanghaememo.dto;
import com.sparta.hanghaememo.entity.Memo;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MemoResponseDto {
    private Long id;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> commentList;

    public MemoResponseDto(Memo memo, List<CommentResponseDto> commentList) {
        this.id = memo.getId();
        this.title = memo.getTitle();
        this.content = memo.getContent();
        this.username = memo.getUser().getUsername();
        this.createdAt = memo.getCreatedAt();
        this.modifiedAt = memo.getModifiedAt();
        this.commentList = commentList;
    }

}