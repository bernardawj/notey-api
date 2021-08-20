package com.bernardawj.notey.dto.project;

import com.bernardawj.notey.dto.shared.BaseListDTO;
import com.bernardawj.notey.dto.shared.filter.FilterDTO;
import com.bernardawj.notey.dto.shared.InputPageDTO;

public class GetManagedProjectDTO extends BaseListDTO<FilterDTO> {

    private int managerId;

    public GetManagedProjectDTO() {
    }

    public GetManagedProjectDTO(int managerId, FilterDTO filter, InputPageDTO inputPage) {
        super(filter, inputPage);
        this.managerId = managerId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }
}
