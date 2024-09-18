package com.toptal.quizhub.app.configurations.auth;

import com.toptal.quizhub.app.configurations.auth.filter.AuthSuccessHandler;
import com.toptal.quizhub.app.configurations.auth.filter.AuthenticationFilter;
import com.toptal.quizhub.app.configurations.auth.filter.AuthorizationFilter;
import com.toptal.quizhub.app.configurations.auth.service.UserDetailsServiceImpl;
import com.toptal.quizhub.app.configurations.auth.util.AuthEntryPointJwt;
import com.toptal.quizhub.rest.constants.RestConstants;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity
@RequiredArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Temporarily suppressing. This needs to be checked asap")
public class WebSecurityConfig {

    @Autowired
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    private final AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    private final AuthorizationFilter authorizationFilter;

    @Autowired
    private final AuthSuccessHandler authSuccessHandler;


    @Autowired
    private final AuthenticationConfiguration authConfig;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
            }
        };
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {

        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .securityMatcher(new AntPathRequestMatcher(RestConstants.API_V1_ENDPOINT + "/**"))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests()
                .anyRequest().authenticated();


        http.authenticationProvider(authenticationProvider());

        final AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager());
        authenticationFilter.setAuthenticationSuccessHandler(authSuccessHandler);
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        authenticationFilter.setAuthenticationSuccessHandler(authSuccessHandler);

        http.addFilterAfter(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
