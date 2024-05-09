package com.mxy.mypro.entity;

import java.util.List;

public class PaginationResult<T> {
    private List<T> data;
    private long total;
    private int totalPages;

    // 构造函数和 getter/setter 方法省略


    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public PaginationResult(List<T> data, long total, int totalPages) {
        this.data = data;
        this.total = total;
        this.totalPages = totalPages;
    }
}