package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.*;

import com.sparta.hanghaememo.entity.Comment;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.member.entity.User;
import com.sparta.hanghaememo.member.entity.UserRoleEnum;
import com.sparta.hanghaememo.member.jwt.JwtUtil;
import com.sparta.hanghaememo.member.repository.UserRepository;
import com.sparta.hanghaememo.repository.CommentRepository;
import com.sparta.hanghaememo.repository.MemoRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository; // service와 db를 연결
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;


    @Transactional//
    public MemoResponseDto createMemo(ModifyRequestDto modifyRequestDto, HttpServletRequest request) {
        // jwt 토큰 확인
        User user = checkJwtToken(request);
        //게시글 작성하면서 댓글을 저장할 자료구조도 미리 함께 생성
        List<CommentResponseDto> commentList = new ArrayList<>();
        Memo memo = new Memo(modifyRequestDto, user); // 메모 클래스를 인스턴스로 만들어서 사용하려면 Memo 부분에 생성자를 추가해줘야함
        memoRepository.saveAndFlush(memo);
        //memoRepository.save(memo);
        return new MemoResponseDto(memo, commentList);
    }


    @Transactional(readOnly = true)//읽기 옵션 추가
    public ResponseEntity<Map<String, List<MemoResponseDto>>> getMemos() {
        //List<MemoResponseDto> memoDtoList = memoRepository.findAllByOrderByCreatedAtDesc().stream().map(MemoResponseDto::new).collect(Collectors.toList());
        List<Memo> memos = memoRepository.findAllByOrderByCreatedAtDesc();
        List<MemoResponseDto> memoDtoList = new ArrayList<>();
        for(Memo memo : memos) {
            memoDtoList.add(new MemoResponseDto(memo, getCommentList(memo.getId())));
        }

        Map<String, List<MemoResponseDto>> result = new HashMap<>();
        result.put("postList", memoDtoList);
        return ResponseEntity.ok().body(result);
    }

    @Transactional(readOnly = true)//읽기 옵션 추가
    public MemoResponseDto selectMemo(Long id) {
        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 글이 존재하지 않습니다.")
        );
        return new MemoResponseDto(memo, getCommentList(id));
    }


    @Transactional
    public MemoModifyDto update(Long id, ModifyRequestDto modifyRequestDto, HttpServletRequest request) {
        // 토큰 체크
        User user = checkJwtToken(request);

        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 글이 존재하지 않습니다.")
        );
        memo.update(modifyRequestDto);
        return new MemoModifyDto(memo);
    }

    @Transactional
    public String deleteMemo(Long id, HttpServletRequest request) {
        // 토큰 체크
        User user = checkJwtToken(request);

        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 글이 존재하지 않습니다.")
        );

        UserRoleEnum userRoleEnum = user.getRole();
        System.out.println("클라이언트 role 확인 = " + userRoleEnum);

        if(userRoleEnum == UserRoleEnum.ADMIN) {
            memoRepository.delete(memo);
            return "게시글을 삭제했습니다.";
        } else {
            if(memo.getUser().getUsername() != user.getUsername()) {
                throw new IllegalArgumentException("다른 사람의 게시글은 삭제 할 수 없습니다.");
            }
            memoRepository.delete(memo);
            return "게시글을 삭제했습니다.";
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

    // 게시글에 달린 댓글 가져오기
    private List<CommentResponseDto> getCommentList(Long memo_id) {
        // 게시글에 달린 댓글 찾아서 작성일 기준 내림차순 정렬
        List<Comment> commentList = commentRepository.findAllByMemo_idOrderByCreatedAtDesc(memo_id);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for(Comment comment : commentList) {
            commentResponseDtoList.add(new CommentResponseDto(comment));
        }
        return commentResponseDtoList;
    }
}
