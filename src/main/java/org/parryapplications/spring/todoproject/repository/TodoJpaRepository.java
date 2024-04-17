package org.parryapplications.spring.todoproject.repository;

import org.parryapplications.spring.todoproject.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoJpaRepository extends JpaRepository<Todo, Integer> {
}
