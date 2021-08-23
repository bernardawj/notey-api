package com.bernardawj.notey.dto.shared;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public abstract class BaseListDTO<FilterDTO> {

    @NotNull(message = "{filter.empty}")
    @Valid
    private FilterDTO filter;

    @NotNull(message = "{sort.empty}")
    @Valid
    private SortDTO sort;

    @NotNull(message = "{inputPage.empty}")
    @Valid
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
