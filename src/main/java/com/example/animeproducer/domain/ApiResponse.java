package com.example.animeproducer.domain;

import java.util.List;

public class ApiResponse {
    private List<Anime> data;
    private Pagination pagination;

    // Getters e setters

    public List<Anime> getData() {
        return data;
    }

    public void setData(List<Anime> data) {
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
