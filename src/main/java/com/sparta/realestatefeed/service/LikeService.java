package com.sparta.realestatefeed.service;


import com.sparta.realestatefeed.entity.*;
import com.sparta.realestatefeed.exception.PostNotFoundException;
import com.sparta.realestatefeed.exception.QnANotFoundException;
import com.sparta.realestatefeed.exception.SelfPostLikeException;
import com.sparta.realestatefeed.repository.ApartRepository;
import com.sparta.realestatefeed.repository.LikeApartRepository;
import com.sparta.realestatefeed.repository.LikeQnARepository;
import com.sparta.realestatefeed.repository.QnARepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class LikeService {

    private final LikeApartRepository likeApartRepository;
    private final LikeQnARepository likeQnARepository;

    private final QnARepository qnARepository;
    private final ApartRepository apartRepository;


    public LikeService(LikeApartRepository likeApartRepository, LikeQnARepository likeQnARepository, QnARepository qnARepository, ApartRepository apartRepository) {

        this.likeApartRepository = likeApartRepository;
        this.likeQnARepository = likeQnARepository;
        this.qnARepository = qnARepository;
        this.apartRepository = apartRepository;
    }

    @Transactional
    public void likeOrUnlike(User user, Long contentId, ContentTypeEnum contentType) {

        Long userId = user.getId();

        if(checkOwnContent(userId, contentId, contentType)) {
            throw new SelfPostLikeException("작성자가 좋아요를 할 수 없습니다.");
        }
        //자기가 만든거 일 때는 종료/예외처리
        if(contentType == ContentTypeEnum.APART_TYPE) {

            Optional<Apart> apartOptional = apartRepository.findFirstById(contentId);
            if(apartOptional.isEmpty()) {


                throw new PostNotFoundException("선택된 뉴스피드는 존재하지 않습니다."); // 예외 처리로 수정
            }
            Optional<LikeApart> existingLike = likeApartRepository.findByUserIdAndId(userId, contentId);
            if (existingLike.isPresent()) {
                likeApartRepository.delete(existingLike.get());
            } else {
                LikeApart like = new LikeApart( user,  apartOptional.get());
                apartOptional.get().updatelikes(1L);
                likeApartRepository.save(like);


            }
        }else{
            QnA qnA = qnARepository.findById(contentId).orElseThrow(() -> new QnANotFoundException("선택된 댓글은 존재하지 않습니다."));

            Optional<LikeQnA> existingLike = likeQnARepository.findByUserIdAndCommentId(userId, contentId);
            if (existingLike.isPresent()) {
                qnA.updatelikes(-1L);
                likeQnARepository.delete(existingLike.get());

            } else {
                LikeQnA like = new LikeQnA( user,  qnA);
                qnA.updatelikes(1L);
                likeQnARepository.save(like);

            }
        }



    }

    private boolean checkOwnContent(Long userId, Long contentId, ContentTypeEnum contentType) {
        //.내부 정보 확인해서 본인이 만든 게시물인지 확인
        if(contentType == ContentTypeEnum.APART_TYPE) {
            Optional<Apart> apartOptional = apartRepository.findById(contentId);
            if (apartOptional.get().getUser().getId()==userId) {
                return true;
            }
        }else{
            Optional<QnA> qnAOptional = qnARepository.findById(contentId);
            if (Objects.equals(qnAOptional.get().getUser().getId(), userId)) {
                return true;
            }
        }
        return false;
    }

}
