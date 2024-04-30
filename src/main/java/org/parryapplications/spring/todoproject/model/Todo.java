package org.parryapplications.spring.todoproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;
    private LocalDate targetedDate;
    private boolean isCompleted;


    public Todo() {
    }

    public Todo(Integer id, String description, LocalDate targetedDate, boolean isCompleted) {
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

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", targetedDate=" + targetedDate +
                ", isCompleted=" + isCompleted +
                '}';
    }
}
