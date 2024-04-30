package org.parryapplications.spring.todoproject.service;

import org.parryapplications.spring.todoproject.dto.TodoDto;
import org.parryapplications.spring.todoproject.model.Todo;
import org.parryapplications.spring.todoproject.repository.TodoJpaRepository;
import org.parryapplications.spring.todoproject.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class TodoServiceImpl {

    Logger logger = LoggerFactory.getLogger(TodoServiceImpl.class);

    private final TodoJpaRepository todoRepository;
    private final CommonUtil commonUtil;

    @Autowired
    public TodoServiceImpl(TodoJpaRepository todoRepository, CommonUtil commonUtil){
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
        } catch(Exception e){
            logger.error("Error while creating todo : ",e);
        }
        return null;
    }

    public TodoDto getTodoById(Integer id){
        Optional<Todo> opTodo = todoRepository.findById(id);
        if(opTodo.isPresent()) {
            logger.info("Retrieved Todo By Id");
            return commonUtil.convertTodoModelToTodoDto(opTodo.get());
        }

        logger.error("Error while retrieving todo by id : "+id);
        return null;

        //        Predicate<? super Todo> predicate = todo -> todo.getId().equals(id);
//        return todoList.stream().filter(predicate).findAny().orElse(null);
    }

    public List<TodoDto> getAllTodos(){
        logger.info("Retrieving all Todos");
        List<TodoDto> list = todoRepository.findAll().stream().map(commonUtil::convertTodoModelToTodoDto).toList();
        return list;
    }

    public String deleteTodoById(Integer id){
        try {
            todoRepository.deleteById(id);
//            todoList.removeIf((todo) -> todo.getId().equals(id));
        } catch(Exception e){
            logger.error("Error while delete todo id : {} :: {} ",id,e.getMessage());
            return "Error while deleting todo id : " + id;
        }
        logger.info("Todo deleted successfully");
        return id + " : Todo Deleted Successfully";
    }

    public TodoDto updateTodo(TodoDto todoDto) {
        try {
            TodoDto todoModelById = getTodoById(todoDto.getId());

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
        } catch(Exception e){
            logger.error("Error updating todo : {} ",e.getMessage());
            return null;
        }
        return null;
    }

    public String deleteAllTodos() {
        try {
            todoRepository.deleteAll();
        } catch(Exception e){
            logger.error("Error while deleting all todos");
            return "Error while deleting all todos";
        }
        logger.info("All todos deleted successfully");
        return "All Todos Deleted Successfully";
    }
}
