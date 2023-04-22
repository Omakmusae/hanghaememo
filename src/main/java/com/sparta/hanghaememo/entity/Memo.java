package com.sparta.hanghaememo.entity;

import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.dto.ModifyRequestDto;
import com.sparta.hanghaememo.member.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Entity
@NoArgsConstructor
public class Memo extends Timestamped { //데이터를 받고 DB와 연결하는 부분
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;

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
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
    }

    public Memo(ModifyRequestDto modifyRequestDto, User user) {
        this.title = modifyRequestDto.getTitle();
        this.content = modifyRequestDto.getContent();
        this.username = user.getUsername();
        this.password = user.getPassword();
    }


    public void update(ModifyRequestDto modifyRequestDto) {
        this.title = modifyRequestDto.getTitle();
        this.content = modifyRequestDto.getContent();
    }



}


