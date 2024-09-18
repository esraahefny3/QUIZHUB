package com.toptal.quizhub.app.configurations.auth.filter;

import com.toptal.quizhub.app.configurations.auth.service.UserDetailsImpl;
import com.toptal.quizhub.app.configurations.auth.util.JwtUtils;
import com.toptal.quizhub.rest.responses.authentication.UserLoginResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {


    @Autowired
    JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication authentication) throws IOException {

        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();


        final String token = jwtUtils.generateTokenFromUsername(userDetails.getUsername());

        final List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        final UserLoginResponseDTO userLoginResponseDTO = UserLoginResponseDTO
                .builder()
                .email(userDetails.getUsername())
                .roles(roles)
                .build();

        final ResponseEntity responseEntity = ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token)
                .body(userLoginResponseDTO);

        convertResponseEntityToHttpServletResponse(responseEntity, res);
    }

    public void convertResponseEntityToHttpServletResponse(ResponseEntity<?> responseEntity,
                                                           HttpServletResponse httpResponse) throws IOException {
        // Set the HTTP status code
        httpResponse.setStatus(responseEntity.getStatusCodeValue());

        // Set the response headers
        final HttpHeaders headers = responseEntity.getHeaders();
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            final String headerName = entry.getKey();
            final List<String> headerValues = entry.getValue();
            for (String headerValue : headerValues) {
                httpResponse.addHeader(headerName, headerValue);
            }
        }

        // Set the response body
        final Object responseBody = responseEntity.getBody();
        if (responseBody != null) {
            final PrintWriter writer = httpResponse.getWriter();
            writer.write(responseBody.toString());
            writer.flush();
        }
    }
}
