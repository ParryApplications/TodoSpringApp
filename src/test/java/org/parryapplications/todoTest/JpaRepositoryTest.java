package org.parryapplications.todoTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.parryapplications.spring.todoproject.TodoSpringApplication;
import org.parryapplications.spring.todoproject.dto.ResultSet;
import org.parryapplications.spring.todoproject.dto.TodoDto;
import org.parryapplications.spring.todoproject.model.Todo;
import org.parryapplications.spring.todoproject.repository.TodoJpaRepository;
import org.parryapplications.spring.todoproject.service.TodoServiceImpl;
import org.parryapplications.spring.todoproject.util.CommonUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * This Test class contains test cases of failing Repository to test the working of src/main/java/org/parryapplications/spring/todoproject/aop/AopConfigurations.java/loggingExceptionOnDaoLayer
 */

@ExtendWith({MockitoExtension.class})
//@ExtendWith({MockitoExtension.class, SpringExtension.class})
//@SpringBootTest(classes = TodoSpringApplication.class)
class JpaRepositoryTest {

    @Mock
    private TodoJpaRepository todoJpaRepository;

    @InjectMocks
//    @Autowired
    private TodoServiceImpl todoServiceImpl;

    @Mock
    private Logger logger;

    @Mock
    private CommonUtil commonUtil;

    @Test
    void saveTodoTest() throws Exception {
        Todo todoModel = new Todo(2, "Test Data", LocalDate.now().plusYears(1), false, "parry");
        TodoDto todoDto = new TodoDto(2, "Test Data", LocalDate.now().plusYears(1), false, "parry");
//        TodoDto todoDto = commonUtil.convertTodoModelToTodoDto(todoModel);

        when(commonUtil.convertTodoDtoToTodoModel(todoDto)).thenReturn(todoModel);
        when(todoJpaRepository.save(todoModel)).thenReturn(todoModel);

        // Act
        TodoDto savedTodo = todoServiceImpl.createTodo(todoDto).getData();

        // Assert
        assertEquals(todoDto, savedTodo);

        // Verify that save method was called on todoJpaRepository with todoModel
//        verify(todoJpaRepository).save(todoModel);
    }

//    @Transactional
//    @Test
    void saveMethodFailingTest() throws Exception {
        Todo todoModel = new Todo(2, "Test Data", LocalDate.now().plusYears(1), false, "parry");
        TodoDto todoDto = new TodoDto(2, "Test Data", LocalDate.now().plusYears(1), false, "parry");
//        TodoDto todoDto = commonUtil.convertTodoModelToTodoDto(todoModel);

//        doThrow(DataAccessException.class).when(todoJpaRepository).save(todoModel);
//        when(todoJpaRepository.save(todoModel)).thenThrow(DataAccessException.class);

        //NOTE: Not required below 2 lines if entity passing as null:
//        when(commonUtil.convertTodoDtoToTodoModel(todoDto)).thenReturn(todoModel);
//        doThrow(new RuntimeException("Simulated DataAccessException")).when(todoJpaRepository).save(any(Todo.class));

        ResultSet<TodoDto> todoResult = todoServiceImpl.createTodo(null);

        //As we are throwing exception intentionally:
        assertEquals("Error while creating todo, Please try again later.", todoResult.getExceptionMessage());

        //Note: Belo assert will fail as in createTodo method i handled exception using try-catch block:
//        assertThrows(RuntimeException.class, () -> {
//            todoServiceImpl.createTodo(todoDto);
//        });

//        verify(todoJpaRepository).save(todoModel);
//        verify(logger).error("Weaver Captured Exception:");
        //Expectation: src/main/java/org/parryapplications/spring/todoproject/aop/AopConfigurations.java/loggingExceptionOnDaoLayer
//        should call and log the Exception capture... msg

//        verify(logger, times(1)).error("Error while creating todo :: Simulated DataAccessException");
    }


    //Not working:
//    @Test
    void deleteAllByUsername_TodosExist_ReturnsCorrectCount() {
        // Arrange
        String username = "DummuTestUser";
        List<Todo> todos = List.of(new Todo(1, "Task 1", LocalDate.now(), false, username), new Todo(2, "Task 2", LocalDate.now(), true, username));
        when(todoJpaRepository.findByUsername(anyString())).thenReturn(todos);

        // Act
        int deletedCount = todoJpaRepository.deleteAllByUsername(username);

        // Assert
        assertEquals(todos.size(), deletedCount);
//        verify(todoJpaRepository, times(1)).deleteAllByUsername(username);
    }

}
