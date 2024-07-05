package com.sparta.realestatefeed.service;

import com.sparta.realestatefeed.dto.ProfileResponseDto;
import com.sparta.realestatefeed.dto.UserRegisterRequestDto;
import com.sparta.realestatefeed.entity.*;
import com.sparta.realestatefeed.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ProfileServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private LikeApartRepository likeApartRepository;

    @Mock
    private LikeQnARepository likeQnARepository;

    @InjectMocks
    private ProfileService profileService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserProfile() {
        // Given
        String userName = "testUser";
        UserRegisterRequestDto userRegisterRequestDto = new UserRegisterRequestDto();
        userRegisterRequestDto.setUserName(userName);
        userRegisterRequestDto.setPassword("password");
        userRegisterRequestDto.setNickName("testNick");
        userRegisterRequestDto.setEmail("test@test.com");
        userRegisterRequestDto.setInfo("testInfo");
        userRegisterRequestDto.setRole(UserRoleEnum.USER);
        User mockUser = new User(userRegisterRequestDto);
        mockUser.setId(1L);

        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(mockUser));

        Apart apart = new Apart();
        apart.setId(1L);
        apart.setUser(mockUser);

        List<LikeApart> mockLikedAparts = new ArrayList<>();
        mockLikedAparts.add(new LikeApart(mockUser, apart));
        when(likeApartRepository.findByUser(mockUser)).thenReturn(mockLikedAparts);
        when(likeApartRepository.countByUserId(mockUser.getId())).thenReturn((long) mockLikedAparts.size());

        QnA qna = new QnA();
        qna.setQnaId(1L);
        qna.setContent("Sample QnA Content");
        qna.setUser(mockUser);
        qna.setApart(apart);

        List<LikeQnA> mockLikedQnAs = new ArrayList<>();
        mockLikedQnAs.add(new LikeQnA(mockUser, qna));
        when(likeQnARepository.findByUser(mockUser)).thenReturn(mockLikedQnAs);
        when(likeQnARepository.countByUserId(mockUser.getId())).thenReturn((long) mockLikedQnAs.size());

        ProfileResponseDto profileResponseDto = profileService.getUserProfile(userName);

        assertEquals(userName, profileResponseDto.getUserName());
        assertEquals(1L, profileResponseDto.getLikedApartCount());
        assertEquals(1L, profileResponseDto.getLikedQnACount());
    }
}
