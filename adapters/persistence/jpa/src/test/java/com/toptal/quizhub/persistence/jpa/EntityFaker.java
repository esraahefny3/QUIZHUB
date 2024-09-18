package com.toptal.quizhub.persistence.jpa;

import com.toptal.quizhub.domain.catalog.BaseFaker;
import com.toptal.quizhub.persistence.jpa.entities.RoleEntity;
import com.toptal.quizhub.persistence.jpa.entities.UserEntity;
import com.toptal.quizhub.persistence.jpa.enums.UserRoleEnum;

import java.util.List;
import java.util.UUID;

public class EntityFaker extends BaseFaker {

    public UserEntity.Builder user() {

        return UserEntity.builder()
                .sid(UUID.randomUUID())
                .username(this.team().name() + this.random().nextLong())
                .createdAt(instant())
                .updatedAt(instant())
                .roles(List.of(RoleEntity.builder()
                        .id(randomInt())
                        .name(String.valueOf(UserRoleEnum.ROLE_USER))
                        .build()));
    }
}
