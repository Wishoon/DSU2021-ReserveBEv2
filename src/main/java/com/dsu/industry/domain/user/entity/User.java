package com.dsu.industry.domain.user.entity;

import com.dsu.industry.global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor  // 기본 생성자 생성
@AllArgsConstructor // 필드값을 포함한 모든 생성자 생성
public class User extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String name;
    private String password;
    private String phone;
    @Embedded
    private Address address;
    @Enumerated(EnumType.STRING)
    @Column(name = "join_type")
    private JoinType joinType;

    // 회원 유형 // Spring Security

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserAuthority> authorities = new HashSet<>();

    @Builder
    public User(String email, String name, String password, String phone) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
    }

    /* 연관관계 메서드 */
    public void addAuthority(UserAuthority userAuthority) {
        authorities.add(userAuthority);
        userAuthority.addUser(this);
    }

    /* 비즈니스 메서드 */
    public void changePwEncode(String password) {

    }

}
