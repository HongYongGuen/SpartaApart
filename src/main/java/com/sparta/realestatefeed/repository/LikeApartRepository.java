package com.sparta.realestatefeed.repository;


import com.sparta.realestatefeed.entity.Apart;
import com.sparta.realestatefeed.entity.LikeApart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikeApartRepository extends JpaRepository<LikeApart, Long> {
    Optional<LikeApart> findByUserIdAndId(Long userId, Long contentId) ;


    @Query("SELECT COUNT(l) FROM LikeApart l WHERE l.user.id = :userId")
    Long countByUserId(Long userId);
}
