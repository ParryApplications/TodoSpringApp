package org.parryapplications.spring.todoproject.dto;

import java.time.LocalDate;

public class TodoDto {
    private Integer id;
    private String description;
    private LocalDate targetedDate;
    private boolean isCompleted;

    public TodoDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getTargetedDate() {
        return targetedDate;
    }

    public void setTargetedDate(LocalDate targetedDate) {
        this.targetedDate = targetedDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    @Override
    public String toString() {
        return "TodoDto{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", targetedDate=" + targetedDate +
                ", isCompleted=" + isCompleted +
                '}';
    }
}
