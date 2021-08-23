package com.bernardawj.notey.dto.project;

import com.bernardawj.notey.dto.shared.BaseListDTO;
import com.bernardawj.notey.dto.shared.InputPageDTO;
import com.bernardawj.notey.dto.shared.SortDTO;
import com.bernardawj.notey.dto.shared.filter.ProjectFilterDTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class GetManagedProjectDTO extends BaseListDTO<ProjectFilterDTO> {

    @NotNull(message = "{project.managerId.empty}")
    @Min(value = 1, message = "{project.managerId.min}")
    private Integer managerId;

    public GetManagedProjectDTO() {
    }

    public GetManagedProjectDTO(ProjectFilterDTO filterDTO, SortDTO sortDTO, InputPageDTO inputPage, Integer managerId) {
        super(filterDTO, sortDTO, inputPage);
        this.managerId = managerId;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }
}
