package com.toptal.quizhub.rest.converters.users.sort;

import com.toptal.quizhub.domain.catalog.sorts.SortRequest;
import com.toptal.quizhub.domain.catalog.sorts.UserSortField;
import com.toptal.quizhub.rest.converters.paginations.sort.AbstractSortConverter;
import com.toptal.quizhub.rest.requests.pagination.sort.Sortable;
import org.springframework.stereotype.Component;

@Component
public class UserSortConverter extends AbstractSortConverter<UserSortField> {

    @Override
    protected SortRequest.FieldSortRequest<UserSortField> convertSortField(Sortable.SortField field) {

        final UserSortField userSortField = UserSortField.valueOf(field.getField().toUpperCase());
        final SortRequest.DirectionRequest direction = SortRequest.DirectionRequest.valueOf(field.getDirection().name());
        return SortRequest.FieldSortRequest.by(userSortField, direction);
    }

    @Override
    protected SortRequest.FieldSortRequest<UserSortField> getDefaultSort() {

        return SortRequest.FieldSortRequest.by(UserSortField.INSERT_ORDER, SortRequest.DirectionRequest.DESC);
    }
}
