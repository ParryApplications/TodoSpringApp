package org.parryapplications.spring.todoproject.service;

import org.parryapplications.spring.todoproject.model.Todo;
import org.parryapplications.spring.todoproject.repository.TodoJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class TodoServiceImpl {

    Logger logger = LoggerFactory.getLogger(TodoServiceImpl.class);

    private final TodoJpaRepository todoRepository;

    @Autowired
    public TodoServiceImpl(TodoJpaRepository todoRepository){
        this.todoRepository = todoRepository;
    }

//    private static List<Todo> todoList = new ArrayList<>();
//
//    static{
//        todoList.add(new Todo(1,"AWS Cloud Practitioner", LocalDate.now().plusYears(1L),false));
//        todoList.add(new Todo(2,"Full Stack Developer", LocalDate.now().plusYears(2L),false));
//        todoList.add(new Todo(3,"Machine Learning", LocalDate.now().plusYears(3L),false));
//    }

    public Todo createTodo(Todo todo){
        todoRepository.save(todo);
        return todo;
    }

    public Todo getTodoById(Integer id){
        Optional<Todo> opTodo = todoRepository.findById(id);

        return opTodo.orElse(null);

        //        Predicate<? super Todo> predicate = todo -> todo.getId().equals(id);
//        return todoList.stream().filter(predicate).findAny().orElse(null);
    }

    public List<Todo> getAllTodos(){
        return todoRepository.findAll();
    }

    public String deleteTodoById(Integer id){
        try {
            todoRepository.deleteById(id);
//            todoList.removeIf((todo) -> todo.getId().equals(id));
        } catch(Exception e){
            logger.error("Error while delete todo id : "+id);
            return "Error while deleting todo id : " + id;
        }
        return id + " : Todo Deleted Successfully";
    }

    public Todo updateTodo(Todo todo){
        Todo todoById = getTodoById(todo.getId());

        if(todoById!=null) {
            return todoRepository.save(todo);
//            todoList.remove(todoById);
//            todoList.add(todo);
//            return todo;
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
        return "All Todos Deleted Successfully";
    }
}
