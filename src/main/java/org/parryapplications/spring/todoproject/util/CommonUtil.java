package org.parryapplications.spring.todoproject.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.parryapplications.spring.todoproject.dto.TodoDto;
import org.parryapplications.spring.todoproject.model.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonUtil {

    private final ObjectMapper objectMapper;
    Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    @Autowired
    public CommonUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    //Model to DTO:
    public TodoDto convertTodoModelToTodoDto(Todo todo) {
        try {
            return objectMapper.convertValue(todo, TodoDto.class);
        } catch (Exception e) {
            logger.error("Error while converting Todo model into Dto : {}", e.getMessage());
        }
        return null;
    }

    //DTO to Model:
    public Todo convertTodoDtoToTodoModel(TodoDto todoDto) {
        try {
            return objectMapper.convertValue(todoDto, Todo.class);
        } catch (Exception e) {
            logger.error("Error while converting Todo Dto into Model : {}", e.getMessage());
        }
        return null;
    }
}
