package com.bernardawj.notey.dto.shared;

public class PaginationDTO {

    private Integer currentPage;
    private Integer totalPages;

    public PaginationDTO() {
    }

    public PaginationDTO(Integer currentPage, Integer totalPages) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
