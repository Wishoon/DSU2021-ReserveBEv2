package com.dsu.industry.domain.user.service;

import com.dsu.industry.domain.user.dto.UserDto;
import com.dsu.industry.domain.user.dto.mapper.UserMapper;
import com.dsu.industry.domain.user.entity.Address;
import com.dsu.industry.domain.user.entity.User;
import com.dsu.industry.domain.user.exception.UserDuplicationException;
import com.dsu.industry.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private UserDto.UserJoinReq userDto;
    private Address address;

    @BeforeEach
    public void before() {
        userDto = UserDto.UserJoinReq.builder()
                .email("test@gmail.com")
                .password("password")
                .name("name")
                .phone("01012345678")
                .addr1_depth_nm("부산")
                .addr2_depth_nm("사하구")
                .addr3_depth_nm("하단2동")
                .addr4_depth_nm("sk 뷰 아파트")
                .build();

        address = new Address("부산", "사하구", "하단2동", "sk뷰 아파트");
    }

    @Test
    @DisplayName("일반회원가입 Dto -> Entity 변환 테스트")
    void userJoinDto_to_Entity() {
        /* given */

        /* when */
        final User user = UserMapper.userJoinDtoToEntity(userDto);

        /* then */
        assertNotNull(user);
        assertThat(user.getEmail()).isEqualTo("test@gmail.com");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getName()).isEqualTo("name");
        assertThat(user.getPhone()).isEqualTo("01012345678");
    }

    @Test
    @DisplayName("회원 Entity -> 회원 정보 Dto 변환 테스트")
    void userEntity_to_userInfoResDto() {
        /* given */
        User user = UserMapper.userJoinDtoToEntity(userDto);

        /* when */
        UserDto.UserInfoRes dto = UserMapper.userEntityToDto(user);

        /* then */
        assertThat(dto.getEmail()).isEqualTo("test@gmail.com");
        assertThat(dto.getName()).isEqualTo("name");
        assertThat(dto.getPhone()).isEqualTo("01012345678");
    }

    @Test
    @DisplayName("회원가입")
    void userJoin() {
        /* given */
        User user = UserMapper.userJoinDtoToEntity(userDto);
        /* when */
        UserDto.UserIdRes res = userService.joinUser(user);

        /* then */
        assertNotNull(res);
        assertThat(user.getId()).isEqualTo(res.getId());
    }

    @Test
    @DisplayName("회원가입 중복 여부 확인")
    void duplicated_user() {
        /* given */
        User user1 = User.builder()
                .email("test@gmail.com")
                .password("password")
                .name("name")
                .phone("01012345678")
                .build();

        User user2 = User.builder()
                .email("test@gmail.com")
                .password("password")
                .name("name")
                .phone("01012345678")
                .build();

        /* when */
        userService.joinUser(user1);

        /* then */
        UserDuplicationException e = assertThrows
                (UserDuplicationException.class, () -> userService.joinUser(user2));

        assertThat(e.getMessage()).isEqualTo("중복된 이메일(아이디) 입니다.");
    }

    @Test
    @DisplayName("유저 정보 변경")
    public void changeUserInfo() {

        /* given */
        User saveUser = userRepository.save(UserMapper.userJoinDtoToEntity(userDto));

        User user = User.builder()
                .email("test2@gmail.com")
                .password("password2")
                .name("name2")
                .phone("01012345678")
                .address(address)
                .build();

        /* when */
        saveUser.changeUserInfo(user);

        /* then */
        assertThat(saveUser.getName()).isEqualTo("name2");
    }
}