package com.bernardawj.notey.dto.task;

import com.bernardawj.notey.dto.shared.BaseListDTO;
import com.bernardawj.notey.dto.shared.InputPageDTO;
import com.bernardawj.notey.dto.shared.SortDTO;
import com.bernardawj.notey.dto.shared.filter.TaskFilterDTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class GetProjectTasksDTO extends BaseListDTO<TaskFilterDTO> {

    @NotNull(message = "{task.projectId.empty}")
    @Min(value = 1, message = "{task.projectId.min}")
    private Integer projectId;

    public GetProjectTasksDTO() {
    }

    public GetProjectTasksDTO(TaskFilterDTO taskFilterDTO, SortDTO sortDTO, InputPageDTO inputPage, Integer projectId) {
        super(taskFilterDTO, sortDTO, inputPage);
        this.projectId = projectId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
