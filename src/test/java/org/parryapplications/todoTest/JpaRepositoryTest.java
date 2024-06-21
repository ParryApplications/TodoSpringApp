package org.parryapplications.todoTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.parryapplications.spring.todoproject.dto.TodoDto;
import org.parryapplications.spring.todoproject.model.Todo;
import org.parryapplications.spring.todoproject.repository.TodoJpaRepository;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * This Test class contains test cases of failing Repository to test the working of src/main/java/org/parryapplications/spring/todoproject/aop/AopConfigurations.java/loggingExceptionOnDaoLayer
 */

@ExtendWith(MockitoExtension.class)
class JpaRepositoryTest {

    @Mock
    private TodoJpaRepository todoJpaRepository;

    @Mock
    private Logger logger;

    @Test
    void saveMethodFailingTest() {
        Todo todoModel = new Todo(1, "Test Data", LocalDate.now().plusYears(1), false);

        doThrow(DataAccessException.class).when(todoJpaRepository).save(any(Todo.class));

        assertThrows(DataAccessException.class, () -> {
            todoJpaRepository.save(todoModel);
        });

        verify(todoJpaRepository).save(todoModel);
        verify(logger).error("Weaver Captured Exception:");
        //Expectation: src/main/java/org/parryapplications/spring/todoproject/aop/AopConfigurations.java/loggingExceptionOnDaoLayer
//        should call and log the Exception capture... msg


    }
}
