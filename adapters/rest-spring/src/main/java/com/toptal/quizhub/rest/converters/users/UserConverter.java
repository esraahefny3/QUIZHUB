package com.toptal.quizhub.rest.converters.users;

import com.toptal.quizhub.commons.converters.Converter;
import com.toptal.quizhub.domain.catalog.User;
import com.toptal.quizhub.rest.requests.authentication.UserSignUpRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserConverter extends Converter<UserSignUpRequestDTO, User> {

    @Override
    @Mapping(target = "sid", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User convert(UserSignUpRequestDTO source);

}

