package com.toptal.quizhub.rest.responses.pagination;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Value;

import java.util.List;

@Value
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "I prefer to suppress these FindBugs warnings")
public class PagedResponse<T> {

    List<T> results;

    PageResponse pagination;
}
