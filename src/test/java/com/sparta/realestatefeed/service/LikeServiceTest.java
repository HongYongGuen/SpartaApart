package com.sparta.realestatefeed.service;

import com.sparta.realestatefeed.dto.ApartResponseDto;
import com.sparta.realestatefeed.entity.*;
import com.sparta.realestatefeed.exception.PostNotFoundException;
import com.sparta.realestatefeed.exception.QnANotFoundException;
import com.sparta.realestatefeed.exception.SelfPostLikeException;
import com.sparta.realestatefeed.repository.ApartRepository;
import com.sparta.realestatefeed.repository.LikeApartRepository;
import com.sparta.realestatefeed.repository.LikeQnARepository;
import com.sparta.realestatefeed.repository.QnARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LikeServiceTest {

    private LikeApartRepository likeApartRepository;
    private LikeQnARepository likeQnARepository;
    private QnARepository qnARepository;
    private ApartRepository apartRepository;
    private LikeService likeService;

    @BeforeEach
    void setUp() {
        likeApartRepository = mock(LikeApartRepository.class);
        likeQnARepository = mock(LikeQnARepository.class);
        qnARepository = mock(QnARepository.class);
        apartRepository = mock(ApartRepository.class);
        likeService = new LikeService(likeApartRepository, likeQnARepository, qnARepository, apartRepository);
    }

    @Test
    void likeOrUnlike_Apart_Success() {
        User user = new User();
        user.setId(1L);
        Apart apart = new Apart();
        apart.setId(1L);
        apart.setUser(user);

        when(apartRepository.findFirstById(1L)).thenReturn(Optional.of(apart));
        when(likeApartRepository.findByUserIdAndId(1L, 1L)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> likeService.likeOrUnlike(user, 1L, ContentTypeEnum.APART_TYPE));
        verify(likeApartRepository, times(1)).save(any(LikeApart.class));
    }

    @Test
    void likeOrUnlike_QnA_Success() {
        User user = new User();
        user.setId(1L);
        QnA qna = new QnA();
        qna.setQnaId(1L);
        qna.setUser(user);



        when(qnARepository.findById(1L)).thenReturn(Optional.of(qna));
        when(likeQnARepository.findByUserIdAndCommentId(1L, 1L)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> likeService.likeOrUnlike(user, 1L, ContentTypeEnum.QNA_TYPE));
        verify(likeQnARepository, times(1)).save(any(LikeQnA.class));
    }

    @Test
    void likeOrUnlike_Apart_SelfLike() {
        User user = new User();
        user.setId(1L);
        Apart apart = new Apart();
        apart.setId(1L);
        apart.setUser(user);

        when(apartRepository.findFirstById(1L)).thenReturn(Optional.of(apart));

        assertThrows(SelfPostLikeException.class, () -> likeService.likeOrUnlike(user, 1L, ContentTypeEnum.APART_TYPE));
    }

    @Test
    void likeOrUnlike_QnA_SelfLike() {
        User user = new User();
        user.setId(1L);
        QnA qna = new QnA();
        qna.setQnaId(1L);
        qna.setUser(user);

        when(qnARepository.findById(1L)).thenReturn(Optional.of(qna));

        assertThrows(SelfPostLikeException.class, () -> likeService.likeOrUnlike(user, 1L, ContentTypeEnum.QNA_TYPE));
    }


}
