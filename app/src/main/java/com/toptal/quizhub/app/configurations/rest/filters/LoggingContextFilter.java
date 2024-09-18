package com.toptal.quizhub.app.configurations.rest.filters;

import com.toptal.quizhub.app.exceptions.FilterException;
import com.toptal.quizhub.commons.constants.Communications;
import com.toptal.quizhub.commons.utils.LoggingUtils;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Order
@Component
@RequiredArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "I prefer to suppress these FindBugs warnings")
public class LoggingContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {

        LoggingUtils.encapsulateWithDefaultLogContext(Communications.HTTP_IN, () -> {
            try {
                chain.doFilter(request, response);
            } catch (IOException | ServletException e) {
                throw new FilterException(e);
            }
        });
    }
}
