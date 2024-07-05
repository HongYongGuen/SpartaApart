package com.sparta.realestatefeed.dto;

import com.sparta.realestatefeed.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileResponseDto {
    String userName;
    String nickName;
    String email;
    String info;
    Long likedApartCount;
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
