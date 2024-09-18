package com.toptal.quizhub.domain.catalog.sorts;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Value;

import java.util.List;

@Value(staticConstructor = "by")
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "I prefer to suppress these FindBugs warnings")
public class SortRequest<T extends NamedField> {

    List<FieldSortRequest<T>> fields;

    public static <T extends NamedField> SortRequest<T> by(FieldSortRequest<T> field) {

        return new SortRequest<>(List.of(field));
    }

    public static <T extends NamedField> SortRequest<T> empty() {

        return new SortRequest<>(List.of());
    }

    public enum DirectionRequest {
        ASC,
        DESC;

        public boolean isAsc() {

            return this.equals(ASC);
        }
    }

    @Value(staticConstructor = "by")
    public static class FieldSortRequest<T extends NamedField> {

        T field;

        DirectionRequest direction;

        public static <T extends NamedField> FieldSortRequest<T> asc(T field) {

            return new FieldSortRequest<>(field, DirectionRequest.ASC);
        }

        public static <T extends NamedField> FieldSortRequest<T> desc(T field) {

            return new FieldSortRequest<>(field, DirectionRequest.DESC);
        }
    }
}
