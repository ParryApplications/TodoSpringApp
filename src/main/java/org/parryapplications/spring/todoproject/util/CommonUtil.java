package org.parryapplications.spring.todoproject.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.parryapplications.spring.todoproject.dto.TodoDto;
import org.parryapplications.spring.todoproject.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonUtil {

    private final ObjectMapper objectMapper;

    @Autowired
    public CommonUtil(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    //Model to DTO:
    public TodoDto convertTodoModelToTodoDto(Todo todo){
        return objectMapper.convertValue(todo, TodoDto.class);
    }

    //DTO to Model:
    public Todo convertTodoDtoToTodoModel(TodoDto todoDto) throws Exception {
        return objectMapper.convertValue(todoDto, Todo.class);
    }
}
