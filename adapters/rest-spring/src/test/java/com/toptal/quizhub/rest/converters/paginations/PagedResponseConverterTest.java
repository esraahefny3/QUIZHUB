package com.toptal.quizhub.rest.converters.paginations;

import com.toptal.quizhub.domain.catalog.results.PagedResult;
import com.toptal.quizhub.rest.RestFaker;
import com.toptal.quizhub.rest.responses.pagination.PagedResponse;
import lombok.Value;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PagedResponseConverterTest {

    private final RestFaker faker = new RestFaker();

    private final PagedResponseConverter subject = new PagedResponseConverter();

    @Test
    void convert_convertsResult() {

        // Given
        final Data data1 = new Data();
        final Data data2 = new Data();
        final boolean hasNext = faker.bool().bool();
        final PagedResult<Data> pagedResult = PagedResult.of(List.of(data1, data2), hasNext);

        // When
        final PagedResponse<DataString> result = subject.convert(pagedResult, data -> new DataString(data.uuid.toString()));

        // Then
        assertThat(result.getPagination().isHasNext()).isEqualTo(hasNext);
        assertThat(result.getResults())
                .extracting(DataString::getString)
                .containsExactly(data1.uuid.toString(), data2.uuid.toString());
    }

    @Value
    private static class Data {

        UUID uuid = UUID.randomUUID();
    }

    @Value
    private static class DataString {

        String string;
    }
}
