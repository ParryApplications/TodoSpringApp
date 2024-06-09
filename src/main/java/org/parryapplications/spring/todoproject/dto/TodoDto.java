package org.parryapplications.spring.todoproject.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class TodoDto {
    private Integer id;
    @Size(min = 5)
    private String description;
    @Future
    private LocalDate targetedDate;
    private boolean isCompleted;
    @NotNull
    @Size(min = 5)
    private String username;

    public TodoDto() {
    }

    public TodoDto(Integer id, String description, LocalDate targetedDate, boolean isCompleted) {
        this.id = id;
        this.description = description;
        this.targetedDate = targetedDate;
        this.isCompleted = isCompleted;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "TodoDto{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", targetedDate=" + targetedDate +
                ", isCompleted=" + isCompleted +
                ", username='" + username + '\'' +
                '}';
    }
}
