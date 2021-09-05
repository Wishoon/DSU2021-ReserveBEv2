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
    private UserMapper userMapper;
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
                .build();

        address = new Address("부산", "사하구", "하단2동", "sk뷰 아파트");
    }

    @Test
    @DisplayName("일반회원가입 Dto -> Entity 변환 테스트")
    void userJoinDto_to_Entity() {
        /* given */

        /* when */
        final User user = UserMapper.INSTANCE.userJoinDtoToEntity(userDto);

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
        User user = userMapper.userJoinDtoToEntity(userDto);

        /* when */
        UserDto.UserInfoRes dto = UserDto.UserInfoRes.toDto(user);

        /* then */
        assertThat(dto.getEmail()).isEqualTo("test@gmail.com");
        assertThat(dto.getName()).isEqualTo("name");
        assertThat(dto.getPhone()).isEqualTo("01012345678");
    }

    @Test
    @DisplayName("회원가입")
    void userJoin() {
        /* given */

        /* when */
        UserDto.UserIdRes res = userService.joinUser(userMapper.userJoinDtoToEntity(userDto));

        /* then */
        assertNotNull(res);
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
        User user = User.builder()
                .email("test2@gmail.com")
                .password("password2")
                .name("name2")
                .phone("01012345678")
                .address(address)
                .build();

        User user_save = userRepository.save(user);

        System.out.println(user_save.getId());
        UserDto.UserInfoReq req = new UserDto.UserInfoReq(
                "name",
                "01093932992",
                address);

        /* when */
        user_save.changeUserInfo(UserDto.UserInfoReq.toEntity(req));

        /* then */
        assertThat(user_save.getName()).isEqualTo("name");
    }
}