package com.toptal.quizhub.http.services.trivia.constants;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TriviaApiConstants {

    @Getter
    @RequiredArgsConstructor
    public enum TriviaSearchDirection {
        DESC("desc"),
        ASC("asc");

        public final String sortDirection;
    }
}
