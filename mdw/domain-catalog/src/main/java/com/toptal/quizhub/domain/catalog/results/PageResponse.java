package com.toptal.quizhub.domain.catalog.results;

import lombok.Value;

@Value(staticConstructor = "of")
public class PageResponse {

    Boolean hasNext;
}
