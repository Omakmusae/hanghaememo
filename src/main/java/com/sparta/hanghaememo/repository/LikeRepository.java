package com.sparta.hanghaememo.repository;

import com.sparta.hanghaememo.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Like findByMemo_IdAndUser_Id(Long memo_id, Long user_id);
    Like findByComment_IdAndUser_Id(Long comment_id, Long user_id);
    void deleteAllByComment_Id(Long comment_id);
    void deleteAllByMemo_Id(Long memo_id);
}
