package com.sparta.realestatefeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "QNA")
@NoArgsConstructor
public class QnA extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_id", nullable = false)
    private Long qnaId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "apart_id")
    private Apart apart;

    private Long likes;

    @OneToMany(mappedBy = "qna", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<LikeQnA> likeQnAS;

    public QnA(String content, User user, Apart apart) {
        this.content = content;
        this.user = user;
        this.apart = apart;
    }

    public void updatelikes(Long num){
        this.likes += num;
    }

    public void changeContent(String content){
        this.content = content;
    }
}
