package com.dsu.industry.domain.user.service;

import com.dsu.industry.domain.user.dto.UserDto;
import com.dsu.industry.domain.user.entity.User;
import com.dsu.industry.domain.user.exception.UserDuplicationException;
import com.dsu.industry.domain.user.exception.UserNotFoundException;
import com.dsu.industry.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto.UserIdRes joinUser(User user) {
        // 회원 중복 확인 로직
        validateDuplicateUser(user);

        // 비밀번호 암호화 로직
        user.changePwEncode(passwordEncoder.encode(user.getPassword()));
        // UserAuthority 추가
//        Optional<Authority> authority = authorityRepository.findById(1L);
//        UserAuthority userAuthority = UserAuthority.toEntity(user, authority.get());
//
//        user.addAuthority(userAuthority);
        User save = userRepository.save(user);

        return UserDto.UserIdRes.builder()
                .id(save.getId())
                .build();
    }

    public UserDto.UserInfoRes user_select(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());

        return UserDto.UserInfoRes.toDto(user);
    }

    public UserDto.UserIdRes user_update(Long userId, UserDto.UserInfoReq userInfoReq) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());

        user.changeUserInfo(userInfoReq.toEntity(userInfoReq));

        return UserDto.UserIdRes.builder()
                .id(user.getId())
                .build();
    }

    // 회원 중복 확인
    private void validateDuplicateUser(User entity) {
        Optional<User> findUser = userRepository.findByEmail(entity.getEmail());
        if(findUser.isPresent()) {
            throw new UserDuplicationException();
        }
    }


}
