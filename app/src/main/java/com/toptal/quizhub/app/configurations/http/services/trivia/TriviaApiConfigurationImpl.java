package com.toptal.quizhub.app.configurations.http.services.trivia;

import com.toptal.quizhub.app.configurations.http.services.HttpOutProperties;
import com.toptal.quizhub.http.services.trivia.TriviaApiConfiguration;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Configuration
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = "http.out.trivia")
@Validated
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Temporarily suppressing. This needs to be checked asap")
public class TriviaApiConfigurationImpl extends HttpOutProperties implements TriviaApiConfiguration {

}
