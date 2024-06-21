package org.parryapplications.spring.todoproject.service;

import jakarta.transaction.Transactional;
import org.parryapplications.spring.todoproject.dto.TodoDto;
import org.parryapplications.spring.todoproject.model.Todo;
import org.parryapplications.spring.todoproject.repository.TodoJpaRepository;
import org.parryapplications.spring.todoproject.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl {

    private final TodoJpaRepository todoRepository;
    private final CommonUtil commonUtil;
    Logger logger = LoggerFactory.getLogger(TodoServiceImpl.class);

    @Autowired
    public TodoServiceImpl(TodoJpaRepository todoRepository, CommonUtil commonUtil) {
        this.todoRepository = todoRepository;
        this.commonUtil = commonUtil;
    }

//    private static List<Todo> todoList = new ArrayList<>();
//
//    static{
//        todoList.add(new Todo(1,"AWS Cloud Practitioner", LocalDate.now().plusYears(1L),false));
//        todoList.add(new Todo(2,"Full Stack Developer", LocalDate.now().plusYears(2L),false));
//        todoList.add(new Todo(3,"Machine Learning", LocalDate.now().plusYears(3L),false));
//    }

    public TodoDto createTodo(TodoDto todoDto) {
        try {
            Todo todoModel = todoRepository.save(commonUtil.convertTodoDtoToTodoModel(todoDto));
            logger.info("Todo created successfully");
            return commonUtil.convertTodoModelToTodoDto(todoModel);
        } catch (Exception e) {
            logger.error("Error while creating todo : ", e);
        }
        return null;
    }

    public TodoDto getTodoById(String username, Integer id) {
        Optional<Todo> opTodo = todoRepository.findById(id);
        if (opTodo.isPresent() && opTodo.get().getUsername().equals(username)) {
            logger.info("Retrieved Todo By Id");
            return commonUtil.convertTodoModelToTodoDto(opTodo.get());
        }

        logger.info("No todo with ID: " + id + " found from your Data");
        return new TodoDto();//sending empty object back

        //        Predicate<? super Todo> predicate = todo -> todo.getId().equals(id);
//        return todoList.stream().filter(predicate).findAny().orElse(null);
    }

    public List<TodoDto> getAllTodos(String username) {
        logger.info("Retrieving all Todos");
        List<TodoDto> list = todoRepository.findByUsername(username).stream().map(commonUtil::convertTodoModelToTodoDto).toList();
        return list;
    }

    public String deleteTodoById(String username, Integer id) {
        try {
            if (getTodoById(username, id).getId() != null) {
                todoRepository.deleteById(id);
                logger.info("Todo deleted successfully");
            } else {
                logger.info("Todo with id:{}, not found!", id);
                return "Todo with ID: " + id + ", not found!";
            }
//            todoList.removeIf((todo) -> todo.getId().equals(id));
        } catch (Exception e) {
            logger.error("Error while delete todo id : {} :: {} ", id, e.getMessage());
            return "Error while deleting todo id : " + id;
        }

        return id + " : Todo Deleted Successfully";
    }

    public TodoDto updateTodo(TodoDto todoDto) {
        try {
            TodoDto todoModelById = getTodoById(todoDto.getUsername(), todoDto.getId());

            //Checking if this todo is existed for modify operation:
            if (!ObjectUtils.isEmpty(todoModelById)) {
                logger.info("Updating Todo...");
                Todo todoModel = todoRepository.save(commonUtil.convertTodoDtoToTodoModel(todoDto));
                logger.info("Todo updated successfully");
                return commonUtil.convertTodoModelToTodoDto(todoModel);
//            todoList.remove(todoById);
//            todoList.add(todo);
//            return todo;
            }
        } catch (Exception e) {
            logger.error("Error updating todo : {} ", e.getMessage());
            return null;
        }
        return null;
    }

    @Transactional
    public String deleteAllTodosByUsername(String username) {
        int count = 0;
        try {
            count = todoRepository.deleteAllByUsername(username);


            if (count > 0)
                logger.info(count + " - Todos Count have been deleted");
            else {
                logger.info("No Todos existed from {}", username);
                return count + " Todos found, Nothing for deletion";
            }
        } catch (Exception e) {
            logger.error("Error while deleting all todos by username :: ", e);
            return "Error while deleting all todos by username";
        }

        return count + " Todos found, All Deleted Successfully";
    }

    @Transactional
    public String deleteAllTodos_AdminsOnly() {
        try {
            todoRepository.deleteAll();
            logger.info("All Todos deleted successfully");
        } catch (Exception e) {
            logger.error("Error while deleting all todos :: ", e);
            return "Error while deleting all todos";
        }
        return "All Todos deleted successfully";
    }

    public List<TodoDto> getAllTodos_AdminsOnly() {
        List<TodoDto> resultTodos = new ArrayList<>();
        try {
            for (Todo model : todoRepository.findAll()) {
                TodoDto dto = commonUtil.convertTodoModelToTodoDto(model);
                resultTodos.add(dto);
            }
        } catch (Exception e) {
            logger.error("Error while retrieving all todos :: ", e);
            return Collections.emptyList();
        }
        logger.info("All Todos retrieved successfully");
        return resultTodos;
    }
}
