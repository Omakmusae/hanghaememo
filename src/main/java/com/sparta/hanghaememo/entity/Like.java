package com.sparta.hanghaememo.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.hanghaememo.member.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Getter
@Entity(name = "Heart")
@NoArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memo_id")
    private Memo memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public Like(User user, Memo memo, Comment comment) {
        this.user = user;
        this.memo = memo;
        this.comment = comment;
    }
}
