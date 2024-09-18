package com.toptal.quizhub.domain.impl.users;

import com.toptal.quizhub.domain.catalog.User;
import com.toptal.quizhub.domain.users.SaveUserDetailsUseCase;
import com.toptal.quizhub.persistence.api.daos.UserDAO;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Temporarily suppressing. This needs to be checked asap")
public class SaveUserDetailsUseCaseImpl implements SaveUserDetailsUseCase {

    private final UserDAO userDAO;

    @Override
    public Optional<User> getBySid(UUID uuid) {

        //throw domain exception lw not found
        return userDAO.findBySid(uuid);

    }

    @Override
    public Optional<User> getByEmail(String email) {

        //throw domain exception lw not found
        return userDAO.findByEmail(email);

    }
}
