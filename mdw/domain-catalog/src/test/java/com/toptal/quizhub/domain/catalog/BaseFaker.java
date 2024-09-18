package com.toptal.quizhub.domain.catalog;

import net.datafaker.Faker;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class BaseFaker extends Faker {

    public static BigDecimal randomBigDecimal(int range) {

        final BigDecimal max = new BigDecimal(range);
        final BigDecimal randFromDouble = new BigDecimal(Math.random());
        BigDecimal actualRandomDec = randFromDouble.multiply(max);
        actualRandomDec = actualRandomDec
                .setScale(2, BigDecimal.ROUND_DOWN);
        return actualRandomDec;
    }

    public Instant futureAtEndOfDay(int days) {

        return this.date()
                .future(days, TimeUnit.DAYS)
                .toInstant()
                .truncatedTo(ChronoUnit.DAYS)
                .plusSeconds(LocalTime.MAX.toSecondOfDay());
    }

    public Instant instant() {

        return this.date().past(10, TimeUnit.DAYS).toInstant();
    }

    public LocalDate localDate() {

        return LocalDate.now().minus(10, ChronoUnit.DAYS).atStartOfDay(ZoneId.systemDefault()).toLocalDate();
    }

    public Boolean randomBoolean() {

        return this.random().nextBoolean();
    }

    @SafeVarargs
    public final <T> T randomChoice(T... values) {

        return values[this.random().nextInt(values.length)];
    }

    public final <T> T randomChoice(Collection<T> values) {

        return values.stream().skip(this.random().nextInt(values.size())).findFirst().get();
    }

    public Long randomLong() {

        return this.random().nextLong();
    }

    public Integer randomInt() {

        return this.random().nextInt();
    }

    public String[] stringArray(int numberOfStrings) {

        final String[] array = new String[numberOfStrings];

        for (int i = 0; i < numberOfStrings; i++) {
            array[i] = this.lorem().word();
        }

        return array;
    }
}
