package com.toptal.quizhub.domain.impl.users;

import com.toptal.quizhub.auth.PasswordEncoderHelper;
import com.toptal.quizhub.domain.catalog.Role;
import com.toptal.quizhub.domain.catalog.User;
import com.toptal.quizhub.domain.catalog.UserRoleEnum;
import com.toptal.quizhub.domain.catalog.exceptions.UserAlreadyExistsException;
import com.toptal.quizhub.domain.catalog.exceptions.UserMissingRoleException;
import com.toptal.quizhub.domain.users.AuthUserUseCase;
import com.toptal.quizhub.persistence.api.daos.RoleDAO;
import com.toptal.quizhub.persistence.api.daos.UserDAO;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Temporarily suppressing. This needs to be checked asap")
public class AuthUserUseCaseImpl implements AuthUserUseCase {

    private final UserDAO userDAO;

    private final RoleDAO roleDAO;

    private final PasswordEncoderHelper passwordEncoderHelper;

    @Override
     public User register(User user) {
        if (userDAO.findByEmail(user.getEmail()).isPresent()) {

            throw UserAlreadyExistsException.useAlreadyExistsException();
        }

        final Optional<List<Role>> roleList = roleDAO.findByNameList(List.of(UserRoleEnum.ROLE_USER.name()));
        if (roleList.isPresent()) {
            user.setRoles(roleList.get());

            //encrypt password
            user.setPassword(passwordEncoderHelper.encode(user.getPassword()));

            return userDAO.insert(user);
        } else {
            throw UserMissingRoleException.userMissingRoleException();
        }

    }

    @Override
    public void logout() {
        //to be implemented allah y5rbytkoooo
    }
}
