package com.bernardawj.notey.dto.shared;

public abstract class BaseListDTO<FilterDTO> {

    private FilterDTO filter;
    private SortDTO sort;
    private InputPageDTO inputPage;

    public BaseListDTO() {
    }

    public BaseListDTO(FilterDTO filter, SortDTO sort, InputPageDTO inputPage) {
        this.filter = filter;
        this.sort = sort;
        this.inputPage = inputPage;
    }

    public FilterDTO getFilter() {
        return filter;
    }

    public void setFilter(FilterDTO filter) {
        this.filter = filter;
    }

    public SortDTO getSort() {
        return sort;
    }

    public void setSort(SortDTO sort) {
        this.sort = sort;
    }

    public InputPageDTO getInputPage() {
        return inputPage;
    }

    public void setInputPage(InputPageDTO inputPage) {
        this.inputPage = inputPage;
    }
}
