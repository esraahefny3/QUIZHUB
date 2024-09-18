package com.toptal.quizhub.persistence.jpa.daos.impl;

import com.toptal.quizhub.domain.catalog.Role;
import com.toptal.quizhub.persistence.api.daos.RoleDAO;
import com.toptal.quizhub.persistence.jpa.converters.roles.RolePersistenceConverter;
import com.toptal.quizhub.persistence.jpa.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoleDAOImpl implements RoleDAO {

    private final RoleRepository roleRepository;

    private final RolePersistenceConverter rolePersistenceConverter;

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name).map(rolePersistenceConverter::convert);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    public Optional<List<Role>> findByNameList(List<String> name) {

        return Optional.of(roleRepository.findByNameIn(name).get()
                .stream().map(rolePersistenceConverter::convert).collect(Collectors.toList()));
    }
}
