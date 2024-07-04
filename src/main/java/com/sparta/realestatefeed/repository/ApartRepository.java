package com.sparta.realestatefeed.repository;

import com.sparta.realestatefeed.entity.Apart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface ApartRepository extends JpaRepository<Apart, Long> {

    Page<Apart> findAllByOrderByModifiedAtDesc(Pageable pageable);
    Page<Apart> findByArea(String area, Pageable pageable);

    Optional<Apart> findFirstById(Long id);

    Optional<Apart> findByUserIdAndId(Long userId, Long contentId);
    @Query("SELECT a FROM Apart a JOIN FETCH a.likeAparts la WHERE la.user.id = :userId")
    Page<Apart> findByUserLikes(Long userId, Pageable pageable);
}
