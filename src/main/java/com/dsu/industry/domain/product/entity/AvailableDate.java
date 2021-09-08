package com.dsu.industry.domain.product.entity;

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
public class AvailableDate {

//    @EmbeddedId
//    private CalendarId calendarId;

    @Id @GeneratedValue
    @Column(name = "available_date_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private LocalDate date;

//    private Long available_maxCnt;

    private boolean isTrue = true;

//    /* 연관관계 메서드 */
//    public void addProduct(Product product) {
//        this.product = product;
//        product.getCalendarList().add(this);
//    }
}
