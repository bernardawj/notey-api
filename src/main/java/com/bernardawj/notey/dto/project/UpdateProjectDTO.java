package com.bernardawj.notey.dto.project;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class UpdateProjectDTO extends BaseProjectDTO {

    @NotNull(message = "{project.id.empty}")
    @Min(value = 1, message = "{project.id.min}")
    private Integer id;

    public UpdateProjectDTO() {
    }

    public UpdateProjectDTO(Integer id, String name, String description, LocalDateTime startAt, LocalDateTime endAt,
                            Integer managerId) {
        super(name, description, startAt, endAt, managerId);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
