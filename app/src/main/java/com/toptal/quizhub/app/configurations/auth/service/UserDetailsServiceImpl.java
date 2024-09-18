package com.toptal.quizhub.app.configurations.auth.service;

import com.toptal.quizhub.domain.catalog.User;
import com.toptal.quizhub.domain.users.GetUserDetailsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    GetUserDetailsUseCase getUserDetailsUseCase;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final User user = getUserDetailsUseCase.getByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));


        return UserDetailsImpl.build(user);
    }
}
