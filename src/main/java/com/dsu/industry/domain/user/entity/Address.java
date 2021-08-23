package com.dsu.industry.domain.user.entity;
import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String addr1_depth_nm;
    private String addr2_depth_nm;
    private String addr3_depth_nm;
    private String addr4_depth_nm;
}
