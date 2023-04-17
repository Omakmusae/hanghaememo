package com.sparta.hanghaememo.dto;

import lombok.Getter;

@Getter
public class MemoRequestDto {//클라이언트에서 넘어오는 데이터를 MemoRequest 객체로 받음
    //DTO는 Data Transfer Objetct의 약자로 데이터 전송을 위한 객체, 데이터 덩어리
    //DTO 객체는 데이터를 담기 위한 필드와, getter/setter 메소드로 이루어져있고
    //DB의 테이블과 매핑될 수 있음
    //직접적으로 데이터에 접근 안하려고 dto를 만듦
    //아래 필드들은 프런트에서 데이터를 받아오는 변수들의 이름으로 구성해야함
    private String title;
    private String content;
    private String author;
    private String password;
}
