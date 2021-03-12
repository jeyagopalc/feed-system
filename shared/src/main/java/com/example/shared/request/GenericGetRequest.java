package com.example.shared.request;

import com.example.shared.model.User;
import com.example.shared.error.ErrorTracker;
import com.example.shared.utils.*;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Set;

import static com.example.shared.constants.SharedConstants.*;

@Data
public class GenericGetRequest {

    private static final Logger logger = LoggerFactory.getLogger(GenericGetRequest.class);

    private User user;
    private FeedType feedType;
    private Set<String> fields;
    private List<Query> queryList;
    private int page;
    private int pageSize;
    private String sortBy;
    private SortType sortType;

    public GenericGetRequest(final User user, final String feedType, final String fields,
                             final String queryList, final int page, final int pageSize,
                             final String sortBy, final String sortType) {

        this.user = user;
        this.feedType = FeedType.fromString(feedType);

        if (StringUtils.isNoneBlank(fields)) {
            this.fields = SharedUtils.parseFields(fields);
        }

        if (StringUtils.isNoneBlank(queryList)) {
            this.queryList = SharedUtils.parseQuery(queryList);
        }

        this.page = page;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
        this.sortType = SortType.fromString(sortType);
    }

    public Pageable getPageable() {
        Sort sort = Sort.by(Sort.Direction.fromString(sortType.toString()), sortBy);
        return PageRequest.of(page, pageSize, sort);
    }

    public boolean validate(final ErrorTracker errorTracker) {
        FieldValidator.validateNonNegativeInteger(PAGE, getPage(), errorTracker);
        FieldValidator.validateNonNegativeInteger(PAGE_SIZE, getPageSize(), errorTracker);
        FieldValidator.validateAllowedParams(SORT_TYPE, sortType,
                Set.of(SortType.ASC, SortType.DESC), errorTracker);

        return errorTracker.isSuccess();
    }
}
