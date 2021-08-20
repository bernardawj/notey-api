package com.bernardawj.notey.dto.project;

import com.bernardawj.notey.dto.shared.BaseListDTO;
import com.bernardawj.notey.dto.shared.filter.FilterDTO;
import com.bernardawj.notey.dto.shared.InputPageDTO;

public class GetAssignedProjectDTO extends BaseListDTO<FilterDTO> {

    private int userId;

    public GetAssignedProjectDTO() {
    }

    public GetAssignedProjectDTO(int userId, FilterDTO filter, InputPageDTO inputPage) {
        super(filter, inputPage);
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
