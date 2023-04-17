package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.DeleteRequestDto;
import com.sparta.hanghaememo.dto.MemoModifyDto;
import com.sparta.hanghaememo.dto.MemoResponseDto;
import com.sparta.hanghaememo.dto.MemoRequestDto;

import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository; // service와 db를 연결

    @Transactional//
    public MemoResponseDto createMemo(MemoRequestDto requestDto) {
        Memo memo = new Memo(requestDto); // 메모 클래스를 인스턴스로 만들어서 사용하려면 Memo 부분에 생성자를 추가해줘야함
        memoRepository.save(memo);
        return new MemoResponseDto(memo);
    }

    @Transactional(readOnly = true)//읽기 옵션 추가
    public List<MemoResponseDto> getMemos() {

        List<Memo> list = memoRepository.findAllByOrderByCreatedAtDesc();
        List<MemoResponseDto> response = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            MemoResponseDto memoResponseDto = new MemoResponseDto(list.get(i));
            response.add(memoResponseDto);
        }
        // List<Memo>를 하나씩 빼서 List<MemoPostResponseDto>에 넣기
        // builder 패턴 -> 이건 나중에 코드 리뷰 때
        // memo에서
        // MemoPostResponseDto 값 넣을 때 setter는 비추
        return response;
    }

    @Transactional(readOnly = true)//읽기 옵션 추가
    public MemoResponseDto selectMemo(Long id) {
        Memo memo = memoRepository.findById(id).get();
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);
        return memoResponseDto;
        // List<MemoSelectResponseDto>
        // List<Memo> id는?
        // Optional 예외처리 다시 찾아보기
        // 생성자 패턴 3가지
    }




    @Transactional
    public MemoModifyDto update(Long id, MemoRequestDto memoRequestDto) {
        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if(memo.getPassword().equals(memoRequestDto.getPassword())) {
            memo.update(memoRequestDto);
        }
        else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return new MemoModifyDto(memo);
    }

    @Transactional
    public Map<String, Boolean> deleteMemo(Long id, DeleteRequestDto requestDto) {
        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if(memo.getPassword().equals(requestDto.getPassword())) {
            memoRepository.delete(memo);
            Map<String, Boolean> response = new HashMap<>();
            response.put("success", true);
            return response;
        }
        else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }


}
