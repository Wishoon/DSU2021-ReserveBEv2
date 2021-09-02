//package com.dsu.industry.domain.user.entity;
//
//import com.dsu.industry.domain.user.dto.UserDto;
//import com.dsu.industry.global.common.BaseEntity;
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Table(name = "USER_AUTHORITY")
//@Entity
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class UserAuthority extends BaseEntity {
//
//    @Id @GeneratedValue
//    @Column(name = "user_authority_id")
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "authority_id")
//    private Authority authority;
//
//    @Builder
//    private UserAuthority(User user, Authority authority) {
//        this.user = user;
//        this.authority = authority;
//    }
//
//    /* 연관관계 메서드 */
//    public void addUser(User user) {
//        this.user = user;
//    }
//
//    /* 비즈니스 메서드 */
//    // UserAuthority Entity 생성
//    public static UserAuthority toEntity(User user, Authority authority) {
//        return UserAuthority.builder()
//                .user(user)
//                .authority(authority)
//                .build();
//    }
//}
