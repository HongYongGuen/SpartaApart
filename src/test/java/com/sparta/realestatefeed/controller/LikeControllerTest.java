package com.sparta.realestatefeed.controller;

import com.sparta.realestatefeed.entity.ContentTypeEnum;
import com.sparta.realestatefeed.entity.User;
import com.sparta.realestatefeed.security.UserDetailsImpl;
import com.sparta.realestatefeed.service.LikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LikeController.class)
class LikeControllerTest {

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
    @WithMockUser
    void toggleLikeNewsFeed_Success() throws Exception {
        Mockito.doNothing().when(likeService).likeOrUnlike(eq(userDetails.getUser()), anyLong(), eq(ContentTypeEnum.APART_TYPE));

        mockMvc.perform(post("/api/aparts/1/likeToggle")
                        .principal(() -> "1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void toggleLikeComment_Success() throws Exception {
        Mockito.doNothing().when(likeService).likeOrUnlike(eq(userDetails.getUser()), anyLong(), eq(ContentTypeEnum.QNA_TYPE));

        mockMvc.perform(post("/api/aparts/1/qna/1/likeToggle")
                        .principal(() -> "1"))
                .andExpect(status().isOk());
    }
}
