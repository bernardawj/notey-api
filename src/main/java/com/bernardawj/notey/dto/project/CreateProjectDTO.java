package com.bernardawj.notey.dto.project;

import java.time.LocalDateTime;

public class CreateProjectDTO extends BaseProjectDTO {

    public CreateProjectDTO() {
    }

    public CreateProjectDTO(String name, String description, LocalDateTime startAt, LocalDateTime endAt,
                            Integer managerId) {
        super(name, description, startAt, endAt, managerId);
    }
}
