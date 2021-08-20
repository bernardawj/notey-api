package com.bernardawj.notey.dto.shared;

public abstract class BaseListDTO<Filter> {

    private Filter filter;
    private InputPageDTO inputPage;

    public BaseListDTO() {
    }

    public BaseListDTO(Filter filter, InputPageDTO inputPage) {
        this.filter = filter;
        this.inputPage = inputPage;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public InputPageDTO getInputPage() {
        return inputPage;
    }

    public void setInputPage(InputPageDTO inputPage) {
        this.inputPage = inputPage;
    }
}
