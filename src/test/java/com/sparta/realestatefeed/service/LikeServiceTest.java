package com.sparta.realestatefeed.service;

import com.sparta.realestatefeed.entity.*;
import com.sparta.realestatefeed.exception.SelfPostLikeException;
import com.sparta.realestatefeed.repository.ApartRepository;
import com.sparta.realestatefeed.repository.LikeApartRepository;
import com.sparta.realestatefeed.repository.LikeQnARepository;
import com.sparta.realestatefeed.repository.QnARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LikeServiceTest {

    @Mock
    private LikeApartRepository likeApartRepository;
    @Mock
    private LikeQnARepository likeQnARepository;
    @Mock
    private QnARepository qnARepository;
    @Mock
    private ApartRepository apartRepository;

    @Mock
    private User user;

    @InjectMocks
    private LikeService likeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void likeOrUnlike_Apart_Success() {
        User user = new User();
        user.setId(1L);
        Apart apart = new Apart();
        apart.setId(1L);
        apart.setUser(user);

        when(likeApartRepository.findByUserIdAndApartId(1L, 1L)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> likeService.likeOrUnlike(user, 1L, ContentTypeEnum.APART_TYPE));
        verify(likeApartRepository, times(1)).save(any(LikeApart.class));
    }

    @Test
    void likeOrUnlike_Apart_AlreadyLiked() {
        User user = new User();
        user.setId(1L);
        Apart apart = new Apart();
        apart.setId(1L);
        apart.setUser(user);

        when(likeApartRepository.findByUserIdAndApartId(1L, 1L)).thenReturn(Optional.of(new LikeApart()));

        assertThrows(SelfPostLikeException.class, () -> likeService.likeOrUnlike(user, 1L, ContentTypeEnum.APART_TYPE));
        verify(likeApartRepository, never()).save(any());
    }

    @Test
    void likeOrUnlike_QnA_Success() {
        User user = new User();
        user.setId(1L);
        QnA qna = new QnA();
        qna.setQnaId(1L);
        qna.setUser(user);

        when(likeQnARepository.findByUserIdAndId(1L, 1L)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> likeService.likeOrUnlike(user, 1L, ContentTypeEnum.QNA_TYPE));
        verify(likeQnARepository, times(1)).save(any(LikeQnA.class));
    }

    @Test
    void likeOrUnlike_QnA_AlreadyLiked() {
        User user = new User();
        user.setId(1L);
        QnA qna = new QnA();
        qna.setQnaId(1L);
        qna.setUser(user);

        when(likeQnARepository.findByUserIdAndId(1L, 1L)).thenReturn(Optional.of(new LikeQnA()));

        assertThrows(SelfPostLikeException.class, () -> likeService.likeOrUnlike(user, 1L, ContentTypeEnum.QNA_TYPE));
        verify(likeQnARepository, never()).save(any());
    }
}
