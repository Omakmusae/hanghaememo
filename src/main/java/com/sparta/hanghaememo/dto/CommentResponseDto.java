package com.sparta.hanghaememo.dto;

import com.sparta.hanghaememo.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String contents;
    private int commentLike;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.contents = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.commentLike=comment.getCommentLike();
    }
}
