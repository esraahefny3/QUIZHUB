package com.toptal.quizhub.persistence.jpa.pagination;

import com.toptal.quizhub.domain.catalog.pages.PageRequest;
import com.toptal.quizhub.domain.catalog.results.PagedResult;
import com.toptal.quizhub.persistence.jpa.Faker;
import lombok.Value;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PaginationPerformerTest {

    private final Faker faker = new Faker();

    private final PaginationPerformer subject = new PaginationPerformer();

    @Test
    void getPagedResult_whenReturnsExtraValue_callsProviderWithExtraElementAndCallsConverterWithTheOriginalLimitAndReturnHasNextToTrue() {

        // Given
        final int originalOffset = faker.domain.number().randomDigit();
        final int originalLimit = faker.domain.number().randomDigitNotZero();
        final PageRequest pageRequest = PageRequest.of(originalOffset, originalLimit);

        final AtomicInteger calledOffset = new AtomicInteger();
        final AtomicInteger calledLimit = new AtomicInteger();

        final AtomicInteger convertCalls = new AtomicInteger(0);

        // When
        final PagedResult<TestClass> result = subject.getPagedResult(pageRequest,
                newPageRequest -> {
                    calledOffset.set(newPageRequest.getOffset());
                    calledLimit.set(newPageRequest.getLimit());
                    return generate(newPageRequest.getLimit());
                },
                testClass -> {
                    convertCalls.getAndIncrement();
                    return testClass;
                });

        // Then
        assertThat(calledOffset).hasValue(originalOffset);
        final int expectedLimitCall = originalLimit + 1;
        assertThat(calledLimit).hasValue(expectedLimitCall);
        assertThat(convertCalls).hasValue(originalLimit);
        assertThat(result.getResults()).hasSize(originalLimit);
        assertThat(result.hasNext()).isTrue();
    }

    @Test
    void getPagedResult_whenReturnsOriginalLimit_returnHasNextFalse() {

        // Given
        final int originalOffset = faker.domain.number().randomDigit();
        final int originalLimit = faker.domain.number().randomDigitNotZero();
        final PageRequest pageRequest = PageRequest.of(originalOffset, originalLimit);

        final AtomicInteger calledOffset = new AtomicInteger();
        final AtomicInteger calledLimit = new AtomicInteger();

        final AtomicInteger convertCalls = new AtomicInteger(0);

        // When
        final PagedResult<TestClass> result = subject.getPagedResult(pageRequest,
                newPageRequest -> {
                    calledOffset.set(newPageRequest.getOffset());
                    calledLimit.set(newPageRequest.getLimit());
                    return generate(originalLimit);
                },
                testClass -> {
                    convertCalls.getAndIncrement();
                    return testClass;
                });

        // Then
        assertThat(calledOffset).hasValue(originalOffset);
        final int expectedLimitCall = originalLimit + 1;
        assertThat(calledLimit).hasValue(expectedLimitCall);
        assertThat(convertCalls).hasValue(originalLimit);
        assertThat(result.getResults()).hasSize(originalLimit);
        assertThat(result.hasNext()).isFalse();
    }

    @Test
    void getPagedResult_whenReturnsLessThanTheOriginalLimit_returnHasNextFalse() {

        // Given
        final int originalOffset = faker.domain.number().randomDigit();
        final int originalLimit = faker.domain.number().randomDigitNotZero() + 10;
        final PageRequest pageRequest = PageRequest.of(originalOffset, originalLimit);

        final AtomicInteger calledOffset = new AtomicInteger();
        final AtomicInteger calledLimit = new AtomicInteger();

        final AtomicInteger convertCalls = new AtomicInteger(0);
        final int nResults = originalLimit - 5;

        // When
        final PagedResult<TestClass> result = subject.getPagedResult(pageRequest,
                newPageRequest -> {
                    calledOffset.set(newPageRequest.getOffset());
                    calledLimit.set(newPageRequest.getLimit());
                    return generate(nResults);
                },
                testClass -> {
                    convertCalls.getAndIncrement();
                    return testClass;
                });

        // Then
        assertThat(calledOffset).hasValue(originalOffset);
        final int expectedLimitCall = originalLimit + 1;
        assertThat(calledLimit).hasValue(expectedLimitCall);
        assertThat(convertCalls).hasValue(nResults);
        assertThat(result.getResults()).hasSize(nResults);
        assertThat(result.hasNext()).isFalse();
    }

    private List<TestClass> generate(int amount) {

        return Stream.generate(TestClass::new)
                .limit(amount)
                .collect(Collectors.toList());
    }

    @Value
    private static class TestClass {

        UUID uuid = UUID.randomUUID();
    }
}
