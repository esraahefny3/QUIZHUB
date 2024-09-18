package com.toptal.quizhub.rest.requests.pagination.sort;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

@Value
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "I prefer to suppress these FindBugs warnings")
public class Sort implements Sortable {

    Set<SortField> sortFields;

    Sort(Set<SortField> sort) {

        this.sortFields = sort;
    }

    public static Sortable by(SortField... sort) {

        return new Sort(Set.of(sort));
    }

    public static Sortable by(Set<SortField> sort) {

        return new Sort(sort);
    }

    @Override
    public String toString() {

        return StringUtils.join(sortFields, ",");
    }
}
