package com.mms.reporting.service.helper;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class PagedList<T> {
    @Setter
    @Getter
    private List<T> items = new ArrayList<>();
    private int pageIndex = 1;
    private int minPageSize = 10;
    @Setter
    @Getter
    private int totalCount;

    @Getter
    private boolean hasNextPage;
    @Getter
    private boolean hasPreviousPage;

    public PagedList() {
    }

    public PagedList(List<T> source, int page, int pageSize, int totalCount) {
        setItems(source);
        setPage(page);
        setPageSize(pageSize);
        setTotalCount(totalCount);

        hasNextPage = hasNextPage();
        hasPreviousPage = hasPreviousPage();
    }

    public int getPage() {
        return pageIndex;
    }

    public void setPage(int page) {
        this.pageIndex = Math.max(page, 1);
    }

    public int getPageSize() {
        return minPageSize;
    }

    public void setPageSize(int pageSize) {
        this.minPageSize = Math.max(pageSize, 0);
    }

    public boolean hasPreviousPage() {
        return getPage() > 1;
    }

    public boolean hasNextPage() {
        return getPage() < getTotalPages();
    }

    public int getTotalPages() {
        return (int) Math.ceil((double) getTotalCount() / getPageSize());
    }

    public int getLowerBound() {
        return getPageSize() * (getPage() - 1) + 1;
    }

    public int getUpperBound() {
        return getTotalPages() == getPage() ? getTotalCount() : getPage() * getPageSize();
    }
}
