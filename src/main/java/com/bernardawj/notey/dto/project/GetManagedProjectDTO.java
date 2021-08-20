package com.bernardawj.notey.dto.project;

import com.bernardawj.notey.dto.shared.BaseListDTO;
import com.bernardawj.notey.dto.shared.InputPageDTO;
import com.bernardawj.notey.dto.shared.filter.ProjectFilterDTO;

public class GetManagedProjectDTO extends BaseListDTO<ProjectFilterDTO> {

    private Integer managerId;

    public GetManagedProjectDTO() {
    }

    public GetManagedProjectDTO(ProjectFilterDTO filterDTO, InputPageDTO inputPage, Integer managerId) {
        super(filterDTO, inputPage);
        this.managerId = managerId;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }
}
