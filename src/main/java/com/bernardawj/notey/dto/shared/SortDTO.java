package com.bernardawj.notey.dto.shared;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SortDTO {

    @NotEmpty(message = "{sort.by.empty}")
    private String by;

    @NotNull(message = "{sort.type.empty}")
    private SortType type;

    public SortDTO() {
    }

    public SortDTO(String by, SortType type) {
        this.by = by;
        this.type = type;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public SortType getType() {
        return type;
    }

    public void setType(SortType type) {
        this.type = type;
    }
}
