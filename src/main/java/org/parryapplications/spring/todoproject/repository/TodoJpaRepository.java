package org.parryapplications.spring.todoproject.repository;

import org.parryapplications.spring.todoproject.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoJpaRepository extends JpaRepository<Todo, Integer> {
    List<Todo> findByUsername(String username);

    int deleteAllByUsername(String username);
}
