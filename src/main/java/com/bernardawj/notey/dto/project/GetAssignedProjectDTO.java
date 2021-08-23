package com.bernardawj.notey.dto.project;

import com.bernardawj.notey.dto.shared.BaseListDTO;
import com.bernardawj.notey.dto.shared.InputPageDTO;
import com.bernardawj.notey.dto.shared.SortDTO;
import com.bernardawj.notey.dto.shared.filter.ProjectFilterDTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class GetAssignedProjectDTO extends BaseListDTO<ProjectFilterDTO> {

    @NotNull(message = "{project.userId.empty}")
    @Min(value = 1, message = "{project.userId.min}")
    private Integer userId;

    public GetAssignedProjectDTO() {
    }

    public GetAssignedProjectDTO(ProjectFilterDTO filterDTO, SortDTO sortDTO, InputPageDTO inputPage, Integer userId) {
        super(filterDTO, sortDTO, inputPage);
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
