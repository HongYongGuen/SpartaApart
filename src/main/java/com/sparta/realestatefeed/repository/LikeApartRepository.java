package com.sparta.realestatefeed.repository;


import com.sparta.realestatefeed.entity.LikeApart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeApartRepository extends JpaRepository<LikeApart, Long> {
    Optional<LikeApart> findByUserIdAndId(Long userId, Long contentId) ;
}
