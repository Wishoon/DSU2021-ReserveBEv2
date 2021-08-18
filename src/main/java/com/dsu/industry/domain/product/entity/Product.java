package com.dsu.industry.domain.product.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id @GeneratedValue
    @Column(name = "product_id")
    private Long id;
    private String name;
    private String sub_name;

    // 카테고리
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    private Long price;
    private Long people_maxCnt;

    // 사진
    @OneToMany(mappedBy = "product")
    @Builder.Default
    private List<Photo> photoList = new ArrayList<>();

    private String description;

    /* 비즈니스 메소드 */
    public void update(Product productRes) {
        this.name = productRes.getName();
        this.sub_name = productRes.getSub_name();
        this.category = productRes.getCategory();
        this.price = productRes.getPrice();
        this.people_maxCnt = productRes.getPeople_maxCnt();
        this.description = productRes.getDescription();
    }
}
