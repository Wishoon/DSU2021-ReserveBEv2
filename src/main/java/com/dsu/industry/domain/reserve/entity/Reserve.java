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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JoinColumn(name = "reserve_peopleCnt")
    private Long people_reserveCnt;

    @JoinColumn(name = "reserve_total_price")
    private Long total_price;
    @JoinColumn(name = "reserve_sales_price")
    private Long sales_price;
    @JoinColumn(name = "reserve_result_price")
    private Long result_price;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "reserve_state")
    private ReserveState reserveState;
    @JoinColumn(name = "reserve_checkIn")
    private LocalDate checkIn;
    @JoinColumn(name = "reserve_checkOut")
    private LocalDate checkOut;


    /* 비즈니스 메서드 */
    public Long calculatingWithCheckInAndCheckOut(LocalDate checkIn, LocalDate checkOut) {
        return ChronoUnit.DAYS.between(checkIn, checkOut);
    }

}
