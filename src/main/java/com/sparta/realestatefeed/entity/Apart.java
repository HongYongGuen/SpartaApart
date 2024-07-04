package com.sparta.realestatefeed.entity;

import com.sparta.realestatefeed.dto.ApartRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "apart")
@Getter
@Setter
@NoArgsConstructor
public class Apart extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apart_id")
    private Long id;

    @Column(name = "apart_name", length = 50)
    private String apartName;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "area", length = 255)
    private String area;

    @Column(name = "sale_price")
    private Long salePrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_saled")
    private ApartStatusEnum isSaled;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "apart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QnA> qnas;

    @OneToMany(mappedBy = "apart", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<LikeApart> likeAparts;

    private Long likes;

    public Apart(ApartRequestDto requestDto, User user) {
        this.apartName = requestDto.getApartName();
        this.address = requestDto.getAddress();
        this.area = requestDto.getArea();
        this.salePrice = requestDto.getSalePrice();
        this.isSaled = requestDto.getIsSaled();
        this.user = user;
        this.likes = 0L;
    }

    public void update(ApartRequestDto requestDto) {
        this.apartName = requestDto.getApartName();
        this.address = requestDto.getAddress();
        this.area = requestDto.getArea();
        this.salePrice = requestDto.getSalePrice();
        this.isSaled = requestDto.getIsSaled();
    }

    public void updatelikes(Long num){
        this.likes += num;
    }
}
