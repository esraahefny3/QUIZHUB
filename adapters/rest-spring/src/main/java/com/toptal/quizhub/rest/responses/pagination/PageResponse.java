package com.toptal.quizhub.rest.responses.pagination;

import lombok.Value;

@Value(staticConstructor = "of")
public class PageResponse {

    boolean hasNext;
}
