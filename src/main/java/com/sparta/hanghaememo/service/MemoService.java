package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository; // service와 db를 연결

    @Transactional//
    public Memo createMemo(MemoRequestDto requestDto) {
        Memo memo = new Memo(requestDto); // 메모 클래스를 인스턴스로 만들어서 사용하려면 Memo 부분에 생성자를 추가해줘야함
        memoRepository.save(memo);
        return memo;
    }
}

