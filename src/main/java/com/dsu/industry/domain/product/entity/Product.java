package com.dsu.industry.domain.product.entity;

import com.dsu.industry.global.common.Address;
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

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    @Builder.Default
    private List<Photo> photoList = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    @Builder.Default
    private List<AvailableDate> availableDateList = new ArrayList<>();

    /** 비즈니스 메소드 */
    // 상품 정보 업데이트
    public void update(Product productRes) {
        this.name = productRes.getName();
        this.sub_name = productRes.getSub_name();
        this.category = productRes.getCategory();
        this.address = productRes.getAddress();
        this.price = productRes.getPrice();
        this.people_maxCnt = productRes.getPeople_maxCnt();
        this.description = productRes.getDescription();
    }
}
