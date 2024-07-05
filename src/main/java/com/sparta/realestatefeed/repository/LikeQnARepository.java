package com.sparta.realestatefeed.repository;


import com.sparta.realestatefeed.entity.LikeQnA;
import com.sparta.realestatefeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LikeQnARepository extends JpaRepository<LikeQnA, Long> {
    Optional<LikeQnA> findByUserAndId(User user, Long contentId);

    @Query("SELECT COUNT(l) FROM LikeQnA l WHERE l.user.id = :userId")
    Long countByUserId(Long userId);

    List<LikeQnA> findAllByUser(User user);

    Object findByUser(User mockUser);


    Object findByUserIdAndId(long userId, long qnaId);
}
