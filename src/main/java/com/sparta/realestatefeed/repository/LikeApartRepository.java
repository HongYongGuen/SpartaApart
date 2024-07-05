package com.sparta.realestatefeed.repository;


import com.sparta.realestatefeed.entity.LikeApart;
import com.sparta.realestatefeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LikeApartRepository extends JpaRepository<LikeApart, Long> {
    Optional<LikeApart> findByUserIdAndId(Long userId, Long contentId) ;


    @Query("SELECT COUNT(l) FROM LikeApart l WHERE l.user.id = :userId")
    Long countByUserId(Long userId);

    List<LikeApart> findAllByUser(User user);

    Object findByUser(User mockUser);

    Object findByUserIdAndApartId(long userid, long apartid);
}
