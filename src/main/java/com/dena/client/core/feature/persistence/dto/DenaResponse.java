package com.dena.client.core.feature.persistence.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DenaResponse {
    @JsonProperty("timestamp")
    private Long timestamp;

    @JsonProperty("count")
    private Long count;

    @JsonProperty("total_count")
    private Long totalCount;

    @JsonProperty("page")
    private Long page;

    @JsonProperty("objects")
    private List<DenaObjectResponse> denaObjectResponseList = new ArrayList<>();

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<DenaObjectResponse> getDenaObjectResponseList() {
        return denaObjectResponseList;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public static final class DenaResponseBuilder {
        private Long timestamp;
        private Long count;
        private Long totalCount;
        private Long page;
        private List<DenaObjectResponse> denaObjectResponseList = new ArrayList<>();

        private DenaResponseBuilder() {
        }

        public static DenaResponseBuilder aDenaResponse() {
            return new DenaResponseBuilder();
        }

        public DenaResponseBuilder withTimestamp(Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public DenaResponseBuilder withCount(Long count) {
            this.count = count;
            return this;
        }

        public DenaResponseBuilder withCount(Integer count) {
            this.count = Long.valueOf(count);
            return this;
        }

        public DenaResponseBuilder withObjectResponseList(List<DenaObjectResponse> denaObjectResponseList) {
            this.denaObjectResponseList = denaObjectResponseList;
            return this;
        }

        public DenaResponseBuilder withTotalCount(long totalCount) {
            this.totalCount = totalCount;
            return this;
        }

        public DenaResponseBuilder withPage(long page) {
            this.page = page;
            return this;
        }

        public DenaResponse build() {
            DenaResponse denaResponse = new DenaResponse();
            denaResponse.setTimestamp(timestamp);
            denaResponse.setCount(count);
            denaResponse.setTotalCount(totalCount);
            denaResponse.setPage(page);
            return denaResponse;
        }
    }
}
