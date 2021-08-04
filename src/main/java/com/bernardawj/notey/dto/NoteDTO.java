package com.bernardawj.notey.dto;

import java.time.LocalDateTime;

public class NoteDTO {

    private Integer id;
    private String title;
    private String writtenContent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public NoteDTO() {
    }

    public NoteDTO(String title, String writtenContent) {
        this.title = title;
        this.writtenContent = writtenContent;
    }

    public NoteDTO(Integer id, String title, String writtenContent, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.writtenContent = writtenContent;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWrittenContent() {
        return writtenContent;
    }

    public void setWrittenContent(String writtenContent) {
        this.writtenContent = writtenContent;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
