package com.toptal.quizhub.rest.responses.authentication;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Temporarily suppressing. This needs to be checked asap")
public class UserLoginResponseDTO {

    String email;

    List<String> roles;
}
