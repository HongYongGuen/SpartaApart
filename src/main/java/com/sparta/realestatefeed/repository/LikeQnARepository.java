package com.sparta.realestatefeed.repository;


import com.sparta.realestatefeed.entity.LikeQnA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeQnARepository extends JpaRepository<LikeQnA, Long> {
    Optional<LikeQnA> findByUserIdAndCommentId(Long userId, Long contentId);
}
