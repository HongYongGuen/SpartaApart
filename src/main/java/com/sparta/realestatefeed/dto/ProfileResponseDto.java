package com.sparta.realestatefeed.dto;

import com.sparta.realestatefeed.entity.User;
import lombok.Getter;

@Getter
public class ProfileResponseDto {
    String userName;
    String nickName;
    String email;
    String info;
    Long likedApartCount; // 내가 좋아요한 게시글 수
    Long likedQnACount;

    public ProfileResponseDto(User user) {
        this.userName = user.getUserName();
        this.nickName = user.getNickName();
        this.email = user.getEmail();
        this.info = user.getInfo();
        this.likedApartCount=0L;
        this.likedQnACount=0L;

    }
}
