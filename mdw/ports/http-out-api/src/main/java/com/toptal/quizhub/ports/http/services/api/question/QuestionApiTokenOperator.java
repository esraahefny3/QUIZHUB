package com.toptal.quizhub.ports.http.services.api.question;

public interface QuestionApiTokenOperator {

    String resetToken(String token);

    String retrieveToken();
}
