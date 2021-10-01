package com.dsu.industry.domain.reserve.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ReserveDetail {
    @Id @GeneratedValue
    @Column(name = "reserve_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserve_id")
    private Reserve reserve;

    private String name;
    private String phone;
    private String requirement;
}
