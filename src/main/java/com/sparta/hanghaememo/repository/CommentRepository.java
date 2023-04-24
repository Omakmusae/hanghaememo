package com.sparta.hanghaememo.repository;

import com.sparta.hanghaememo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByMemo_idOrderByCreatedAtDesc(Long id); //댓글 작성날짜 순으로 내림차순 정렬
    void deleteAllByMemo_id(Long id);
    Optional<Comment> findByIdAndUser_username(Long id, String username);
}
