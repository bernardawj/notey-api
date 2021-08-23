package com.bernardawj.notey.dto.shared;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class InputPageDTO {

    @NotNull(message = "{inputPage.pageNo.empty}")
    @Min(value = 1, message = "{inputPage.pageNo.min}")
    private Integer pageNo;

    @NotNull(message = "{inputPage.pageSize.empty}")
    @Min(value = 1, message = "{inputPage.pageSize.min}")
    private Integer pageSize;

    public InputPageDTO() {
    }

    public InputPageDTO(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
