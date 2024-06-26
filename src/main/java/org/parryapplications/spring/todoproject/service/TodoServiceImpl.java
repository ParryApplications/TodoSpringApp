package org.parryapplications.spring.todoproject.service;

import jakarta.transaction.Transactional;
import org.parryapplications.spring.todoproject.dto.ResultSet;
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

    public ResultSet<TodoDto> createTodo(TodoDto todoDto) {
        ResultSet<TodoDto> resultSet = new ResultSet<>();
        Todo todoModel = null;
        try {
            todoModel = todoRepository.save(commonUtil.convertTodoDtoToTodoModel(todoDto));
            logger.info("Todo created successfully");

            resultSet.setData(commonUtil.convertTodoModelToTodoDto(todoModel));
            resultSet.setSuccessCount(1);
            resultSet.setSuccessMessage("Todo created successfully");
        } catch (Exception e) {
            logger.error("Error while creating todo :: {}", e.getMessage());
            resultSet.setData(null);
            resultSet.setExceptionCount(1);
            resultSet.setExceptionMessage("Error while creating todo, Please try again later.");
        }

        return resultSet;
    }

    public ResultSet<TodoDto> getTodoById(String username, Integer id) {
        try {
            Optional<Todo> opTodo = todoRepository.findById(id);
            if (opTodo.isPresent()) {
                logger.info("Retrieved Todo By Id");
                return new ResultSet<>(commonUtil.convertTodoModelToTodoDto(opTodo.get()), 1, "Todo retrieved successfully");
            }
        } catch (Exception e) {
            logger.error("Error while retrieving todo with id:{} of User:{} \n Exception :: {}", id, username, e.getMessage());
            return new ResultSet<>("Error while retrieving Todo", 1);
        }
        logger.info("No todo with ID: {} found", id);
        return new ResultSet<>(1, "No todo found");//sending empty object back
    }

    public ResultSet<List<TodoDto>> getAllTodos(String username) {
        List<TodoDto> list = null;
        try {
            list = todoRepository.findByUsername(username).stream().map(commonUtil::convertTodoModelToTodoDto).toList();
        } catch (Exception e) {
            logger.error("Error while retrieving all todos of User :: {}", e.getMessage());
            return new ResultSet<>("Error while retrieving Todos", 1);
        }
        logger.info("Retrieved all Todos");
        return new ResultSet<>(list, 1, "Todos retrieved successfully");
    }

    public ResultSet<String> deleteTodoById(String username, Integer id) {
        try {
            if (getTodoById(username, id).getData().getId() != null) {
                todoRepository.deleteById(id);
            } else {
                logger.info("Todo with id:{}, not found!", id);
                return new ResultSet<>(1, "No todo found");
            }
        } catch (Exception e) {
            logger.error("Error while delete todo id : {} :: {} ", id, e.getMessage());
            return new ResultSet<>("Error while deleting Todo", 1);
        }
        logger.info("Todo with ID: {}, Deleted Successfully", id);
        return new ResultSet<>(1, "Todo deleted successfully");
    }

    public ResultSet<TodoDto> updateTodo(TodoDto todoDto) {
        Todo todoModel = null;
        try {
            TodoDto todoModelById = getTodoById(todoDto.getUsername(), todoDto.getId()).getData();

            if (!ObjectUtils.isEmpty(todoModelById)) {
                todoModel = todoRepository.save(commonUtil.convertTodoDtoToTodoModel(todoDto));
            } else {
                logger.info("Todo with id:{}, not found", todoDto.getId());
                return new ResultSet<>(1, "No todo found");
            }
        } catch (Exception e) {
            logger.error("Error updating todo : {} \n Exception : {} ", todoDto, e.getMessage());
            return new ResultSet<>("Error while updating Todo", 1);
        }
        logger.info("Todo : {}, Updated successfully", todoDto);
        return new ResultSet<>(commonUtil.convertTodoModelToTodoDto(todoModel), 1, "Todo updated successfully");
    }

    @Transactional
    public ResultSet<String> deleteAllTodosByUsername(String username) {
        int count = 0;
        try {
            count = todoRepository.deleteAllByUsername(username);

            if (count > 0) logger.info("{} Todos have been deleted", count);
            else {
                logger.info("No Todos existed from {}", username);
                return new ResultSet<>(1, count + " Todos found, Nothing for deletion");
            }
        } catch (Exception e) {
            logger.error("Error while deleting all todos by username :: {}", e.getMessage());
            return new ResultSet<>("Error while deleting all Todos", 1);
        }
        return new ResultSet<>(1, count + " Todos found, All deleted successfully");
    }

    @Transactional
    public ResultSet<String> deleteAllTodos_AdminsOnly() {
        try {
            todoRepository.deleteAll();
        } catch (Exception e) {
            logger.error("Error while deleting all users todos :: {}", e.getMessage());
            return new ResultSet<>("Error while deleting all Users Todos", 1);
        }
        logger.info("All Users Todos deleted successfully");
        return new ResultSet<>(1, "All Users Todos deleted successfully");
    }

    public ResultSet<List<TodoDto>> getAllTodos_AdminsOnly() {
        List<TodoDto> resultTodos = new ArrayList<>();
        try {
            for (Todo model : todoRepository.findAll()) {
                TodoDto dto = commonUtil.convertTodoModelToTodoDto(model);
                resultTodos.add(dto);
            }
        } catch (Exception e) {
            logger.error("Error while retrieving all users todos :: {}", e.getMessage());
            return new ResultSet<>("Error while retrieving all Users Todos", 1);
        }
        logger.info("All Users Todos retrieved successfully");
        return new ResultSet<>(resultTodos, 1, "All Users Todos retrieved successfully");
    }
}
