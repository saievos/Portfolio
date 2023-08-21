package com.techelevator.model;

import java.util.List;

public class RootObject {
    private List<Data> data;
    private Pagination pagination;


    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
