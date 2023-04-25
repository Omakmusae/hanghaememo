package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.Exception.CustomException;
import com.sparta.hanghaememo.dto.CommentRequestDto;
import com.sparta.hanghaememo.dto.CommentResponseDto;
import com.sparta.hanghaememo.entity.Comment;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.member.entity.User;
import com.sparta.hanghaememo.member.entity.UserRoleEnum;
import com.sparta.hanghaememo.member.jwt.JwtUtil;
import com.sparta.hanghaememo.member.repository.UserRepository;
import com.sparta.hanghaememo.message.Message;
import com.sparta.hanghaememo.repository.CommentRepository;
import com.sparta.hanghaememo.repository.MemoRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

import static com.sparta.hanghaememo.Exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final MemoRepository memoRepository;
    private final JwtUtil jwtUtil;
    // 댓글 작성
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, HttpServletRequest request) {
        // 클라이언트의 토큰 체크
        User user = checkJwtToken(request);
        // 게시글 DB 저장 유무 확인
        Memo memo = memoRepository.findById(commentRequestDto.getMemo_id()).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND)
        );
        //댓글 저장
        Comment comment = commentRepository.saveAndFlush(new Comment(commentRequestDto,memo, user));
        return new CommentResponseDto(comment);
    }
    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long memo_id, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        // 클라이언트의 토큰 체크
        User user = checkJwtToken(request);

        Comment comment;
        UserRoleEnum userRoleEnum = user.getRole();
        // 권한 확인 후, 관리자가 아니면 작성자인지 확인
        if(userRoleEnum == UserRoleEnum.ADMIN) {
            comment = commentRepository.findById(memo_id).orElseThrow(
                    () -> new CustomException(COMMENT_NOT_FOUND)
            );
            comment.update(commentRequestDto, user);
            return new CommentResponseDto(comment);
        } else {
            // 작성자 일치 여부 확인
            comment = commentRepository.findByIdAndUser_username(memo_id, user.getUsername()).orElseThrow(
                    () -> new CustomException(AUTHOR_NOT_SAME_MOD)
            );
        }
        comment.update(commentRequestDto, user);
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    @Transactional
    public Message deleteComment(Long memo_id, HttpServletRequest request) {
        // 토큰 체크
        User user = checkJwtToken(request);

        UserRoleEnum userRoleEnum = user.getRole();
        Comment comment;
        // 권한 확인 후, 관리자가 아니면 작성자인지 확인
        if(userRoleEnum == UserRoleEnum.ADMIN) {
            // 댓글이 DB에 있는지 확인
            comment = commentRepository.findById(memo_id).orElseThrow(
                    () -> new CustomException(COMMENT_NOT_FOUND)
            );
        } else {
            // 작성자 일치 여부 확인
            comment = commentRepository.findByIdAndUser_username(memo_id, user.getUsername()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "작성자만 삭제할 수 있습니다")
            );
        }
        commentRepository.deleteById(memo_id);
        return new Message("댓글 삭제 성공", 200);
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
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다.");
            }
            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new CustomException(CANNOT_FOUND_USERNAME)
            );
            return user;

        }
        return null;
    }
}
