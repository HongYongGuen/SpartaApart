package com.sparta.realestatefeed.controller;

import com.sparta.realestatefeed.dto.ApartResponseDto;
import com.sparta.realestatefeed.dto.QnAResponseDto;
import com.sparta.realestatefeed.entity.ContentTypeEnum;
import com.sparta.realestatefeed.entity.User;
import com.sparta.realestatefeed.security.UserDetailsImpl;
import com.sparta.realestatefeed.service.LikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(LikeController.class)
public class LikeControllerTest {

    @Autowired
    private MockMvc mockMvc;



    @MockBean
    private LikeService likeService;

    private UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(1L);
        user.setUserName("testUser");
        user.setPassword("testPassword");
        userDetails = new UserDetailsImpl(user);
    }

    @Test
    @WithMockUser(username = "testUser", password = "testPassword")
    void toggleLikeNewsFeed_Success() throws Exception {

        Mockito.doNothing().when(likeService).likeOrUnlike(eq(userDetails.getUser()), anyLong(), eq(ContentTypeEnum.APART_TYPE));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/aparts/1/likeToggle"));

        resultActions
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser", password = "testPassword")
    void toggleLikeComment_Success() throws Exception {
        Mockito.doNothing().when(likeService).likeOrUnlike(eq(userDetails.getUser()), anyLong(), eq(ContentTypeEnum.QNA_TYPE));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/aparts/1/qna/1/likeToggle"));

        resultActions
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser", password = "testPassword")
    void getLikedAparts_Success() throws Exception {
        // Given
        Page<ApartResponseDto> dummyPage = Mockito.mock(Page.class);
        Mockito.when(likeService.getLikedAparts(anyLong(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(dummyPage);

        // When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/likedAparts"));

        // Then
        resultActions
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser", password = "testPassword")
    void getLikedQnAs_Success() throws Exception {
        // Given
        Page<QnAResponseDto> dummyPage = Mockito.mock(Page.class);
        Mockito.when(likeService.getLikedQnAs(anyLong(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(dummyPage);

        // When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/likedQnAs"));

        // Then
        resultActions
                .andExpect(status().isOk());
    }
}
