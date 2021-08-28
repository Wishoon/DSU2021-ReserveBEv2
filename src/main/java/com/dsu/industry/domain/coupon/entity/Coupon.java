package com.dsu.industry.domain.coupon.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Coupon {
    @Id @GeneratedValue
    @Column(name = "coupon_io")
    private Long id;
}
