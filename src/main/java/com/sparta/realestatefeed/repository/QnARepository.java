package com.sparta.realestatefeed.repository;

import com.sparta.realestatefeed.entity.QnA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QnARepository extends JpaRepository<QnA, Long> {

    List<QnA> findByApartId(Long apartId);

    @Query("SELECT q FROM QnA q JOIN FETCH q.likeQnAS lq WHERE lq.user.id = :userId")
    Page<QnA> findByUserLikes(Long userId, Pageable pageable);

}
