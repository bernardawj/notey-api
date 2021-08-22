package com.bernardawj.notey.dto.shared;

public class SortDTO {

    private String by;
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
