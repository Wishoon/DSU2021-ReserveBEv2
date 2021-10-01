package com.dsu.industry.domain.user.entity;

import com.dsu.industry.global.common.Address;
import com.dsu.industry.global.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor  // 기본 생성자 생성
@AllArgsConstructor // 필드값을 포함한 모든 생성자 생성
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "user_email")
    private String email;
    @Column(name = "user_name")
    private String name;
    @Column(name = "user_password")
    private String password;
    @Column(name = "user_phone")
    private String phone;
    @Embedded
    private Address address;

    @Column(name = "user_provider_id")
    private String providerId;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_authProvider")
    private AuthProvider authProvider;
    @Column(name = "user_emailVerified")
    @Builder.Default
    private Boolean emailVerified = false;

    // 회원 유형 // Spring Security
    @Enumerated(EnumType.STRING)
    @Column(name = "user_authority")
    private Authority authority;


    /** 비즈니스 메서드 */
    // 비밀번호 변경 메서드
    public void changePwEncode(String password) {
        this.password = password;
    }

    // 유저 속성 추가 or 변경 메서드
    public void changeAuthority(Authority authority) {
        this.authority = authority;
    }

    // 유저 정보 변경 메서드
    public void changeUserInfo(User user) {
        this.name = user.getName();
        this.phone = user.getPhone();
        this.address = user.getAddress();
    }
}
