package com.dsu.industry.global.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Embeddable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String addr1_depth_nm;
    private String addr2_depth_nm;
    private String addr3_depth_nm;
    private String addr4_depth_nm;
}
