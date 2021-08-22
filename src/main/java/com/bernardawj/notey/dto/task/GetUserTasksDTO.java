package com.bernardawj.notey.dto.task;

import com.bernardawj.notey.dto.shared.BaseListDTO;
import com.bernardawj.notey.dto.shared.InputPageDTO;
import com.bernardawj.notey.dto.shared.SortDTO;
import com.bernardawj.notey.dto.shared.filter.TaskFilterDTO;

public class GetUserTasksDTO extends BaseListDTO<TaskFilterDTO> {

    private Integer userId;

    public GetUserTasksDTO() {
    }

    public GetUserTasksDTO(TaskFilterDTO taskFilterDTO, SortDTO sortDTO, InputPageDTO inputPage, Integer userId) {
        super(taskFilterDTO, sortDTO, inputPage);
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
