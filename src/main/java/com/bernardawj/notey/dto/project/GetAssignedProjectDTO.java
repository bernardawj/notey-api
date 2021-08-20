package com.bernardawj.notey.dto.project;

import com.bernardawj.notey.dto.shared.BaseListDTO;
import com.bernardawj.notey.dto.shared.InputPageDTO;
import com.bernardawj.notey.dto.shared.filter.ProjectFilterDTO;

public class GetAssignedProjectDTO extends BaseListDTO<ProjectFilterDTO> {

    private Integer userId;

    public GetAssignedProjectDTO() {
    }

    public GetAssignedProjectDTO(ProjectFilterDTO filterDTO, InputPageDTO inputPage, Integer userId) {
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
