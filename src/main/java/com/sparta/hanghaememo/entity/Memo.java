package com.sparta.hanghaememo.entity;

import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.dto.ModifyRequestDto;
import com.sparta.hanghaememo.member.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import org.springframework.core.annotation.Order;

import javax.persistence.*;
//import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Memo extends Timestamped { //데이터를 받고 DB와 연결하는 부분
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "memo_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 일대다 관계 설정
    @OneToMany(mappedBy = "memo", cascade = CascadeType.REMOVE) // cascade 설정은 외래키로 연결된 테이블의 데이터까지 삭제
    @OrderBy("createdAt desc")//댓글은 작성 날짜 기준 내림차순 정렬
    private List<Comment> commentList;

//    public Memo(String username, String contents) {
//        this.username = username;
//        this.contents = contents;
//    }

    public Memo(MemoRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.user = user;
    }

    public Memo(ModifyRequestDto modifyRequestDto, User user) {
        this.title = modifyRequestDto.getTitle();
        this.content = modifyRequestDto.getContent();
        this.user = user;
    }


    public void update(ModifyRequestDto modifyRequestDto) {
        this.title = modifyRequestDto.getTitle();
        this.content = modifyRequestDto.getContent();
    }

}


