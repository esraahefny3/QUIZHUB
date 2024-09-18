package com.toptal.quizhub.persistence.jpa.repositories.jpa;

import com.toptal.quizhub.persistence.jpa.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findByName(String name);

    Optional<List<RoleEntity>> findByNameIn(List<String> nameList);
}
