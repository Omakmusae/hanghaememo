package com.sparta.hanghaememo.repository;


import com.sparta.hanghaememo.dto.MemoResponseDto;
import com.sparta.hanghaememo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findAllByOrderByCreatedAtDesc();//게시물 조회  관련,
    // findAllByOrderByModifiedAtDesc() -> public List<Memo> getMemos() -> public List<Memo> getMemos() controller에서 GET 방식 통신할 때 사용
    //List<Memo> findAllByOrderByModifiedAtDesc();
}