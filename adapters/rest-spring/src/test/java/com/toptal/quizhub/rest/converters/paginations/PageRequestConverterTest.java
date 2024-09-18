package com.toptal.quizhub.rest.converters.paginations;

import com.toptal.quizhub.domain.catalog.pages.PageRequest;
import com.toptal.quizhub.rest.configurations.PaginationConfiguration;
import com.toptal.quizhub.rest.requests.pagination.Pageable;
import lombok.Value;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PageRequestConverterTest {

    @Test
    void convertWithMaxLimitReturnsMaxLimit() {

        //Given
        final PaginationConfiguration paginationConfiguration = PaginationConfigurationImpl.of(5, 20, 20);

        final PageRequestConverter subject = new PageRequestConverter(paginationConfiguration);

        //When
        final PageRequest pageRequest = subject.convertExportItems(50, 0);

        //Then
        assertThat(pageRequest).isNotNull();
        assertThat(pageRequest.getOffset()).isEqualTo(0);
        assertThat(pageRequest.getLimit()).isEqualTo(20);
    }

    @Test
    void convertWithMinLimitReturnsMaxLimit() {

        //Given
        final PaginationConfiguration paginationConfiguration = PaginationConfigurationImpl.of(5, 20, 20);

        final PageRequestConverter subject = new PageRequestConverter(paginationConfiguration);

        //When
        final PageRequest pageRequest = subject.convertExportItems(19, 0);

        //Then
        assertThat(pageRequest).isNotNull();
        assertThat(pageRequest.getOffset()).isEqualTo(0);
        assertThat(pageRequest.getLimit()).isEqualTo(19);
    }

    @Test
    void convert_whenHasNullValues_returnsDefaultOffsetAndPagination() {

        //Given
        final PaginationConfiguration paginationConfiguration = PaginationConfigurationImpl.of(5, 20, 20);
        final Pageable pageable = PageableImpl.of(null, null);

        final PageRequestConverter subject = new PageRequestConverter(paginationConfiguration);

        //When
        final PageRequest pageRequest = subject.convert(pageable);

        //Then
        assertThat(pageRequest).isNotNull();
        assertThat(pageRequest.getOffset()).isEqualTo(0);
        assertThat(pageRequest.getLimit()).isEqualTo(5);
    }

    @Test
    void convert_whenLimitIsHigherThanAllowed_returnsMaxLimit() {

        //Given
        final PaginationConfiguration paginationConfiguration = PaginationConfigurationImpl.of(5, 20, 20);
        final Pageable pageable = PageableImpl.of(0, 50);

        final PageRequestConverter subject = new PageRequestConverter(paginationConfiguration);

        //When
        final PageRequest pageRequest = subject.convert(pageable);

        //Then
        assertThat(pageRequest).isNotNull();
        assertThat(pageRequest.getOffset()).isEqualTo(0);
        assertThat(pageRequest.getLimit()).isEqualTo(20);
    }

    @Test
    void convert_whenNullOffset_returnsWithOffset0AndRequestedLimit() {

        //Given
        final PaginationConfiguration paginationConfiguration = PaginationConfigurationImpl.of(5, 20, 20);
        final Pageable pageable = PageableImpl.of(null, 10);

        final PageRequestConverter subject = new PageRequestConverter(paginationConfiguration);

        //When
        final PageRequest pageRequest = subject.convert(pageable);

        //Then
        assertThat(pageRequest).isNotNull();
        assertThat(pageRequest.getOffset()).isEqualTo(0);
        assertThat(pageRequest.getLimit()).isEqualTo(10);
    }

    @Test
    void convert_whenValidValues_returnsRequestedPagination() {

        //Given
        final PaginationConfiguration paginationConfiguration = PaginationConfigurationImpl.of(5, 20, 20);
        final Pageable pageable = PageableImpl.of(10, 10);

        final PageRequestConverter subject = new PageRequestConverter(paginationConfiguration);

        //When
        final PageRequest pageRequest = subject.convert(pageable);

        //Then
        assertThat(pageRequest).isNotNull();
        assertThat(pageRequest.getOffset()).isEqualTo(10);
        assertThat(pageRequest.getLimit()).isEqualTo(10);
    }

    @Value(staticConstructor = "of")
    private static class PageableImpl implements Pageable {

        Integer offset;

        Integer limit;
    }

    @Value(staticConstructor = "of")
    private static class PaginationConfigurationImpl implements PaginationConfiguration {

        Integer defaultLimit;

        Integer maxLimit;

        Integer maxExportLimit;
    }
}
