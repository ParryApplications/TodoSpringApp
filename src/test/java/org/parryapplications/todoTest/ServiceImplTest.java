package org.parryapplications.todoTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.parryapplications.spring.todoproject.dto.TodoDto;
import org.parryapplications.spring.todoproject.model.Todo;
import org.parryapplications.spring.todoproject.repository.TodoJpaRepository;
import org.parryapplications.spring.todoproject.service.TodoServiceImpl;
import org.parryapplications.spring.todoproject.util.CommonUtil;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceImplTest {

    @InjectMocks
    private TodoServiceImpl todoServiceImpl;

    @Mock
    private TodoJpaRepository todoRepository;

    @Mock
    private CommonUtil commonUtil;

//    @Test
    void calculatorTest(){
        int a = 10;
        int b = 20;

        assertEquals(30,a+b,"Calculator Test Failed");
    }

    @Test
    void updateTodoTest() throws Exception {
        //Sample Test Data:
        TodoDto todoDto = new TodoDto(1,"Test Data", LocalDate.now().plusYears(1), false);
        Todo todoModel = new Todo(1,"Test Data", LocalDate.now().plusYears(1), false);
        Optional<Todo> todoOptional = Optional.of(todoModel);

        //Mockito example:
        when(todoRepository.findById(anyInt())).thenReturn(todoOptional);
        when(commonUtil.convertTodoModelToTodoDto(any(Todo.class))).thenReturn(todoDto);
        when(commonUtil.convertTodoDtoToTodoModel(any(TodoDto.class))).thenReturn(todoModel);
        when(todoRepository.save(any(Todo.class))).thenReturn(todoModel);

        //Invocation of method, which dev needs to test:
        TodoDto resultDto = todoServiceImpl.updateTodo(todoDto);

        //Verification after test case ran:
        assertNotNull(resultDto);
        assertEquals(todoDto, resultDto, "Result not matching with expected dto data");

        verify(todoRepository, times(1)).findById(anyInt());
        verify(todoRepository,times(1)).save(any(Todo.class));
    }
}
