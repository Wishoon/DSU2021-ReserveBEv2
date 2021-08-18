package com.dsu.industry.domain.user.dto.mapper;

import com.dsu.industry.domain.user.dto.UserDto;
import com.dsu.industry.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    /* final static */
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", constant = "0L")
    User userJoinDtoToEntity(UserDto.UserJoinReq userJoinReq);


}
