package com.toptal.quizhub.persistence.jpa.converters.roles;

import com.toptal.quizhub.commons.converters.Converter;
import com.toptal.quizhub.domain.catalog.Role;
import com.toptal.quizhub.persistence.jpa.entities.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RolePersistenceConverter extends Converter.BiDirectional<RoleEntity, Role> {

    @Override
    @Mapping(target = "users", ignore = true)
    Role convert(RoleEntity input);

    @Override
    @Mapping(target = "userEntities", ignore = true)
    RoleEntity convertToSource(Role input);
}
