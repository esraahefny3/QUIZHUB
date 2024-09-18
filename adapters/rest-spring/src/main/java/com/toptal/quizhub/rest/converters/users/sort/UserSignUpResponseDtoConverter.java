package com.toptal.quizhub.rest.converters.users.sort;

import com.toptal.quizhub.commons.converters.Converter;
import com.toptal.quizhub.domain.catalog.User;
import com.toptal.quizhub.rest.responses.authentication.UserSignUpResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserSignUpResponseDtoConverter extends Converter<User, UserSignUpResponseDTO> {

    @Override
    UserSignUpResponseDTO convert(User source);

}

