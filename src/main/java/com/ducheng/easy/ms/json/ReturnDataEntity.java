package com.ducheng.easy.ms.json;

/**
 * 返回的查询对象实体
 */
public class ReturnDataEntity {

    private Object results;

    private Integer offset;

    private Integer limit;

    private Integer total;

    public Object getResults() {
        return results;
    }

    public void setResults(Object results) {
        this.results = results;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public ReturnDataEntity() {
    }
}
