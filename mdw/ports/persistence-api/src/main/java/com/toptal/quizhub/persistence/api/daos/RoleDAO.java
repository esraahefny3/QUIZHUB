package com.toptal.quizhub.persistence.api.daos;

import com.toptal.quizhub.domain.catalog.Role;

import java.util.List;
import java.util.Optional;

public interface RoleDAO {

    Optional<Role> findByName(String name);

    Optional<List<Role>> findByNameList(List<String> name);

}
