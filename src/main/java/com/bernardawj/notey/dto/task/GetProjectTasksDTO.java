package com.bernardawj.notey.dto.task;

import com.bernardawj.notey.dto.shared.BaseListDTO;
import com.bernardawj.notey.dto.shared.InputPageDTO;
import com.bernardawj.notey.dto.shared.filter.TaskFilterDTO;

public class GetProjectTasksDTO extends BaseListDTO<TaskFilterDTO> {

    private Integer projectId;

    public GetProjectTasksDTO() {
    }

    public GetProjectTasksDTO(TaskFilterDTO taskFilterDTO, InputPageDTO inputPage, Integer projectId) {
        super(taskFilterDTO, inputPage);
        this.projectId = projectId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
