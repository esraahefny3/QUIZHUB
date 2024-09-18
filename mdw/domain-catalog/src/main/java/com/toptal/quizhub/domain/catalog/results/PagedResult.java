package com.toptal.quizhub.domain.catalog.results;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.List;

@Value(staticConstructor = "of")
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "I prefer to suppress these FindBugs warnings")
public class PagedResult<T> {

    List<T> results;

    @Accessors(fluent = true)
    boolean hasNext;
}
