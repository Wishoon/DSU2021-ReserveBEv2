package com.dsu.industry.domain.product.entity;

import com.dsu.industry.domain.user.entity.Address;
import com.dsu.industry.global.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "product_id")
    private Long id;
    private String name;
    private String sub_name;

    // 카테고리
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Embedded
    private Address address;

    private Long price;
    private Long people_maxCnt;

    private String description;

    // 사진
    @OneToMany(mappedBy = "product")
    @Builder.Default
    private List<Photo> photoList = new ArrayList<>();

//    // 예약 가능 여부 날짜 리스트
//    @OneToMany(mappedBy = "product")
//    @Builder.Default
//    private List<AvailableDate> calendarList = new ArrayList<>();


    /* 연관관계 메서드 */
//    public void addCalender(List<AvailableDate> availableDates) {
//       this.calendarList = availableDates;
//    }

    /* 비즈니스 메소드 */
    /**
     * 상품에 대한 정보 업데이트
     * @param productRes
     */
    public void update(Product productRes) {
        this.name = productRes.getName();
        this.sub_name = productRes.getSub_name();
        this.category = productRes.getCategory();
        this.price = productRes.getPrice();
        this.people_maxCnt = productRes.getPeople_maxCnt();
        this.description = productRes.getDescription();
    }
}
