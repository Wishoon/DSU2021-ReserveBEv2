package com.dsu.industry.domain.product.entity;

import com.dsu.industry.global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Photo extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Enumerated(EnumType.STRING)
    private PhotoType photoType;

    private String photoUrl;

    /** 비즈니스 메서드 */

    public void update(Photo photo) {
        this.product = photo.getProduct();
        this.photoUrl = photo.getPhotoUrl();
    }
}
