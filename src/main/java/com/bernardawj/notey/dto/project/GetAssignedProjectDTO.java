package com.bernardawj.notey.dto.project;

import com.bernardawj.notey.dto.shared.BaseListDTO;
import com.bernardawj.notey.dto.shared.filter.FilterDTO;
import com.bernardawj.notey.dto.shared.InputPageDTO;

public class GetAssignedProjectDTO extends BaseListDTO<FilterDTO> {

    private Integer userId;

    public GetAssignedProjectDTO() {
    }

    public GetAssignedProjectDTO(FilterDTO filterDTO, InputPageDTO inputPage, Integer userId) {
        super(filterDTO, inputPage);
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
