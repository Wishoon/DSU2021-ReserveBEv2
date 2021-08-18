용package com.dsu.industry.domain.user.service;

import com.dsu.industry.domain.user.dto.UserDto;
import com.dsu.industry.domain.user.dto.mapper.UserMapper;
import com.dsu.industry.domain.user.entity.User;
import com.dsu.industry.domain.user.exception.UserDuplicationException;
import com.dsu.industry.domain.user.service.UserService;
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

    private UserDto.UserJoinReq userJoinDto;

    @BeforeEach
    public void before() {
        userJoinDto = UserDto.UserJoinReq.builder()
                .email("test@gmail.com")
                .password("password")
                .name("name")
                .phone("01012345678")
                .build();
    }

    @Test
    @DisplayName("일반회원가입 Dto -> Entity 변환 테스트")
    void userJoinDto_to_Entity() {
        /* given */

        /* when */
        final User user = UserMapper.INSTANCE.userJoinDtoToEntity(userJoinDto);

        /* then */
        assertNotNull(user);
        assertThat(user.getEmail()).isEqualTo("test@gmail.com");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getName()).isEqualTo("name");
        assertThat(user.getPhone()).isEqualTo("01012345678");
    }

    @Test
    @DisplayName("회원가입")
    void userJoin() {
        /* given */

        /* when */
        UserDto.UserIdRes res = userService.joinUser(userMapper.userJoinDtoToEntity(userJoinDto));

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

        assertThat(e.getMessage()).isEqualTo("중복된 회원 입니다.");
    }
}