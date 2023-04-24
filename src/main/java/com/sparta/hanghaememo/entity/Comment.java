package com.sparta.hanghaememo.entity;

import com.sparta.hanghaememo.dto.CommentRequestDto;
import com.sparta.hanghaememo.member.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Getter
@Entity
@NoArgsConstructor
public class Comment  extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "memo_id" )//게시글 id
    private Memo memo;

    @Column(nullable = false)
    private String content;

    // 다대일 관계 설정, JoinColumn으로 FK로 설정
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(CommentRequestDto commentRequestDto, Memo memo, User user) {
        this.content = commentRequestDto.getContent();
        this.memo = memo;
        this.user= user;
    }

    public void update(CommentRequestDto commentRequestDto, User user) {
        this.content = commentRequestDto.getContent();
        this.user = user;
    }
}
