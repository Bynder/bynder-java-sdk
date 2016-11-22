package com.getbynder.sdk.domain;

/**
 *
 * @author daniel.sequeira
 */
public class MediaAssetRequest {

    private String keyword;
    private Integer limit;
    private Integer page;
    private String propertyOptionId;

    public MediaAssetRequest(final String keyword, final Integer limit, final Integer page, final String propertyOptionId) {
        this.keyword = keyword;
        this.limit = limit;
        this.page = page;
        this.propertyOptionId = propertyOptionId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(final String keyword) {
        this.keyword = keyword;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(final Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(final Integer page) {
        this.page = page;
    }

    public String getPropertyOptionId() {
        return propertyOptionId;
    }

    public void setPropertyOptionId(final String propertyOptionId) {
        this.propertyOptionId = propertyOptionId;
    }
}
