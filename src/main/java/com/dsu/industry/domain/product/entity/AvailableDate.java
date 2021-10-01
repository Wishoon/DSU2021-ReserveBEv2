package com.dsu.industry.domain.product.entity;

import com.dsu.industry.global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AvailableDate extends BaseEntity {

//    @EmbeddedId
//    private CalendarId calendarId;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "available_date_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private LocalDate date;

//    private Long available_maxCnt;
    @Builder.Default
    private boolean isTrue = true;

//    /* 연관관계 메서드 */
//    public void addProduct(Product product) {
//        this.product = product;
//        product.getCalendarList().add(this);
//    }
}
