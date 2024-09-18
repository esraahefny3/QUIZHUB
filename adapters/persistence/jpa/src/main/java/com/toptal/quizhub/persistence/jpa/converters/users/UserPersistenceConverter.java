package com.toptal.quizhub.persistence.jpa.converters.users;

import com.toptal.quizhub.commons.converters.Converter;
import com.toptal.quizhub.domain.catalog.Role;
import com.toptal.quizhub.domain.catalog.User;
import com.toptal.quizhub.persistence.jpa.entities.RoleEntity;
import com.toptal.quizhub.persistence.jpa.entities.UserEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", builder = @org.mapstruct.Builder(disableBuilder = true))
public interface UserPersistenceConverter extends Converter.BiDirectional<UserEntity, User> {

    @Override
    @Mapping(target = "roles", ignore = true)
    User convert(UserEntity input);

    @AfterMapping
    default void mapUserRoles(UserEntity input, @MappingTarget User user) {
        user.setRoles(input.getRoles().stream().map(r ->
                Role.builder().id(r.getId()).name(r.getName()).build()).collect(Collectors.toList()));
    }

    @Override
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "roles", ignore = true)
    UserEntity convertToSource(User input);

    @AfterMapping
    default void mapUserEntityRoles(@MappingTarget UserEntity userEntity, User input) {
        userEntity.setRoles(input.getRoles().stream().map(r ->
                RoleEntity.builder().id(r.getId()).name(r.getName()).build()).collect(Collectors.toList()));
    }

}
