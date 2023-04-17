package com.sparta.hanghaememo.entity;

import com.sparta.hanghaememo.dto.MemoRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Getter
@Entity
@NoArgsConstructor
public class Memo extends Timestamped { //데이터를 받고 DB와 연결하는 부분
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String password;

//    public Memo(String username, String contents) {
//        this.username = username;
//        this.contents = contents;
//    }

    public Memo(MemoRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.author = requestDto.getAuthor();
        this.password = requestDto.getPassword();
    }



    public void update(MemoRequestDto memoRequestDto) {
        this.author = memoRequestDto.getAuthor();
        this.content = memoRequestDto.getContent();
    }



}


