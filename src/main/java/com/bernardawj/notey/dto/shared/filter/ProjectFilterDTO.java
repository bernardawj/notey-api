package com.bernardawj.notey.dto.shared.filter;

public class ProjectFilterDTO extends FilterDTO {

    private Integer daysBeforesDueDate;

    public ProjectFilterDTO() {
    }

    public ProjectFilterDTO(String searchString, Integer daysBeforesDueDate) {
        super(searchString);
        this.daysBeforesDueDate = daysBeforesDueDate;
    }

    public Integer getDaysBeforesDueDate() {
        return daysBeforesDueDate;
    }

    public void setDaysBeforesDueDate(Integer daysBeforesDueDate) {
        this.daysBeforesDueDate = daysBeforesDueDate;
    }
}
