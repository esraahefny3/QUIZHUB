package com.toptal.quizhub.domain.catalog.results;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "I prefer to suppress these FindBugs warnings")
public class PagedResponse<T> {

    List<T> results;

    PageResponse pagination;

    public static <T> PagedResponse<T> empty() {

        return PagedResponse.of(List.of(), PageResponse.of(false));
    }
}
