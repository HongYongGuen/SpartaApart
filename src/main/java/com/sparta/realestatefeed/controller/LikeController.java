package com.sparta.realestatefeed.controller;


import com.sparta.realestatefeed.entity.ContentTypeEnum;
import com.sparta.realestatefeed.entity.User;
import com.sparta.realestatefeed.security.UserDetailsImpl;
import com.sparta.realestatefeed.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Validated
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/api/aparts/{apartId}/likeToggle")
    public ResponseEntity<String> toggleLikeNewsFeed(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                             @PathVariable Long apartId) {

        try {
            User user = userDetails.getUser();
            likeService.likeOrUnlike(user, apartId, ContentTypeEnum.APART_TYPE);
            return new ResponseEntity<>("좋아요 토글 성공", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("좋아요 토글 실패", HttpStatus.BAD_REQUEST);
        }
    }



    @PostMapping("aparts/{apartId}/qna/{qnaId}/likeToggle")
    public ResponseEntity<String> toggleLikeComment( @AuthenticationPrincipal UserDetailsImpl userDetails,
                                             @PathVariable Long qnaId) {

        try {
            User user = userDetails.getUser();
            likeService.likeOrUnlike(user, qnaId, ContentTypeEnum.QNA_TYPE);
            return new ResponseEntity<>("좋아요 토글 성공", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("좋아요 토글 실패", HttpStatus.BAD_REQUEST);
        }
    }


}
