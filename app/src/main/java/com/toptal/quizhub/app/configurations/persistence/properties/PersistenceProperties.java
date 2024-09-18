package com.toptal.quizhub.app.configurations.persistence.properties;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "persistence")
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Temporarily suppressing. This needs to be checked asap")
public class PersistenceProperties {

    private boolean generateStatistics;

    private boolean autoCloseSession;

    private boolean nonContextualLobCreation;

    private boolean useGetGeneratedKeys;

    private String jdbcTimeZone;

    private Debug debug;

    @Data
    public static class Debug {

        private boolean showSql;

        private boolean formatSql;

        private boolean showSqlComments;

        private long logQuerySlowerThan = 0;
    }
}
