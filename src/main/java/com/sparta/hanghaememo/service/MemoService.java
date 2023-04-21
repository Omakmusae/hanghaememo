package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.DeleteRequestDto;
import com.sparta.hanghaememo.dto.MemoModifyDto;
import com.sparta.hanghaememo.dto.MemoResponseDto;
import com.sparta.hanghaememo.dto.MemoRequestDto;

import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.member.entity.User;
import com.sparta.hanghaememo.member.jwt.JwtUtil;
import com.sparta.hanghaememo.member.repository.UserRepository;
import com.sparta.hanghaememo.repository.MemoRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository; // service와 db를 연결
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    @Transactional//
    public MemoResponseDto createMemo(MemoRequestDto requestDto, HttpServletRequest request) {
        // jwt 토큰 확인
        User user = checkJwtToken(request);
        Memo memo = new Memo(requestDto); // 메모 클래스를 인스턴스로 만들어서 사용하려면 Memo 부분에 생성자를 추가해줘야함
        memo.setUsername(user.getUsername());
        memoRepository.saveAndFlush(memo);
        //memoRepository.save(memo);
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
        return response;
    }

    @Transactional(readOnly = true)//읽기 옵션 추가
    public MemoResponseDto selectMemo(Long id) {
        Memo memo = memoRepository.findById(id).get();
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);
        return memoResponseDto;
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
    public String deleteMemo(Long id, DeleteRequestDto requestDto) {
        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if(memo.getPassword().equals(requestDto.getPassword())) {
            memoRepository.delete(memo);
//            Map<String, Boolean> response = new HashMap<>();
//            response.put("success", true);
            return "게시글을 삭제했습니다.";
        }
        else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    public User checkJwtToken(HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시글 접근 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            return user;

        }
        return null;
    }
}
