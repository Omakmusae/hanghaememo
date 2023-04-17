package com.sparta.hanghaememo.entity;

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
    private String username;

    @Column(nullable = false)
    private String contents;

}


