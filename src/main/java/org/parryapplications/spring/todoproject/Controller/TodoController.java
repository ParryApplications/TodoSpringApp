package org.parryapplications.spring.todoproject.Controller;

import org.parryapplications.spring.todoproject.model.Todo;
import org.parryapplications.spring.todoproject.service.TodoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
public class TodoController {

    private final TodoServiceImpl todoService;

    @Autowired
    public TodoController(TodoServiceImpl todoService){
        this.todoService = todoService;
    }

    @PostMapping("/todos")
    public Todo createTodo(@RequestBody Todo todo){
        return todoService.createTodo(todo);
    }

    @GetMapping("/todos/{id}")
    public Todo getTodoById(@PathVariable("id") Integer id){
        return todoService.getTodoById(id);
    }

    @GetMapping("/todos")
    public List<Todo> getAllTodos(){
        return todoService.getAllTodos();
    }

    @DeleteMapping("/todos/{id}")
    public void deleteTodoById(@PathVariable("id") Integer id){
        todoService.deleteTodoById(id);
    }

    @PutMapping("/todos")
    public Todo updateTodo(@RequestBody Todo todo){
        return todoService.updateTodo(todo);
    }

    @DeleteMapping("/todos")
    public String deleteAllTodos(){
        return todoService.deleteAllTodos();
    }
}
