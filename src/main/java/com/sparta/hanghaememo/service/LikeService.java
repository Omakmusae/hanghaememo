package com.sparta.hanghaememo.service;


import com.sparta.hanghaememo.Exception.CustomException;
import com.sparta.hanghaememo.entity.Comment;
import com.sparta.hanghaememo.entity.Like;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.member.entity.User;
import com.sparta.hanghaememo.message.Message;
import com.sparta.hanghaememo.repository.CommentRepository;
import com.sparta.hanghaememo.repository.LikeRepository;
import com.sparta.hanghaememo.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sparta.hanghaememo.Exception.ErrorCode.COMMENT_NOT_FOUND;
import static com.sparta.hanghaememo.Exception.ErrorCode.MEMO_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final MemoRepository memoRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    // Post 좋아요
    @Transactional
    public Message memoLike(Long memo_id, User user) {

        // 1. 게시글 조회
        Memo memo = memoRepository.findById(memo_id).orElseThrow(
                () -> new CustomException(MEMO_NOT_FOUND)
        );

        // 2. LikeRepository 에서 postId, userId 로 조회
        Like like = likeRepository.findByMemo_IdAndUser_Id(memo_id, user.getId());

        // 좋아요 없으면 DB에 추가
        if (like == null) {
            likeRepository.save(new Like(user, memo, null));
            memo.like();
            return new Message("게시글 좋아요 성공", 200);
        } else { // 좋아요 있으면 DB에서 제거
            likeRepository.deleteById(like.getId());
            memo.unlike();
            return new Message("게시글 좋아요 취소 성공", 200);
        }
    }

    // Comment 좋아요
    @Transactional
    public Message commentLike(Long comment_id, User user) {

        // 1. 댓글 조회
        Comment comment = commentRepository.findById(comment_id).orElseThrow(
                () -> new CustomException(COMMENT_NOT_FOUND)
        );

        // 2. LikeRepository 에서 commentId, userId 로 조회
        Like like = likeRepository.findByComment_IdAndUser_Id(comment_id, user.getId());

        // 좋아요 없으면 DB에 추가
        if (like == null) {
            likeRepository.save(new Like(user, null, comment));
            comment.like();
            return new Message("댓글 좋아요 성공", 200);
        } else { // 좋아요 있으면 DB에서 제거
            likeRepository.deleteById(like.getId());
            comment.unlike();
            return new Message("댓글 좋아요 취소 성공", 200);
        }
    }
}
