package com.example.animeproducer.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pagination {
    @JsonProperty("last_visible_page")
    private int lastVisiblePage;
    @JsonProperty("has_next_page")
    private boolean hasNextPage;
    @JsonProperty("current_page")
    private int currentPage;
    private Items items;

    // Getters e setters

    public int getLastVisiblePage() {
        return lastVisiblePage;
    }

    public void setLastVisiblePage(int lastVisiblePage) {
        this.lastVisiblePage = lastVisiblePage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }
}
