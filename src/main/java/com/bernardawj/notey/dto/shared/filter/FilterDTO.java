package com.bernardawj.notey.dto.shared.filter;

public class FilterDTO {

    private String searchString;

    public FilterDTO() {
    }

    public FilterDTO(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
