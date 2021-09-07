package com.dsu.industry.domain.reserve.entity;

import com.dsu.industry.domain.product.entity.Product;
import com.dsu.industry.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Reserve {

    @Id @GeneratedValue
    @Column(name = "reserve_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "reserve")
    @Builder.Default
    private List<ReserveDetail> reserveDetailList = new ArrayList<>();

    // 예약 상세 정보
    private Long people_reserveCnt;

    private Long total_price;
    private Long sales_price;
    private Long result_price;

    @Enumerated(EnumType.STRING)
    private ReserveType reserveType;

    private LocalDate checkIn;
    private LocalDate checkOut;


    /* 비즈니스 메서드 */
    public Long calculatingWithCheckInAndCheckOut(LocalDate checkIn, LocalDate checkOut) {
        return ChronoUnit.DAYS.between(checkIn, checkOut);
    }

}
