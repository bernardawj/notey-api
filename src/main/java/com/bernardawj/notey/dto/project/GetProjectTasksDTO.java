package com.bernardawj.notey.dto.project;

import com.bernardawj.notey.dto.shared.BaseListDTO;
import com.bernardawj.notey.dto.shared.InputPageDTO;
import com.bernardawj.notey.dto.shared.filter.TaskFilterDTO;

public class GetProjectTasksDTO extends BaseListDTO<TaskFilterDTO> {

    private int projectId;

    public GetProjectTasksDTO() {
    }

    public GetProjectTasksDTO(TaskFilterDTO filter, InputPageDTO inputPage, int projectId) {
        super(filter, inputPage);
        this.projectId = projectId;
    }

    public int getProjectId() {
        return projectId;
    }
}
