package org.parryapplications.spring.todoproject.Controller;

import org.parryapplications.spring.todoproject.dto.TodoDto;
import org.parryapplications.spring.todoproject.model.Todo;
import org.parryapplications.spring.todoproject.service.TodoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
public class TodoWebserviceImpl {

    private final TodoServiceImpl todoService;

    @Autowired
    public TodoWebserviceImpl(TodoServiceImpl todoService){
        this.todoService = todoService;
    }

    @PostMapping("/todos")
    public TodoDto createTodo(@RequestBody TodoDto todoDto){
        return todoService.createTodo(todoDto);
    }

    @GetMapping("/todos/{id}")
    public TodoDto getTodoById(@PathVariable("id") Integer id){
        return todoService.getTodoById(id);
    }

    @GetMapping("/todos")
    public List<TodoDto> getAllTodos(){
        return todoService.getAllTodos();
    }

    @DeleteMapping("/todos/{id}")
    public void deleteTodoById(@PathVariable("id") Integer id){
        todoService.deleteTodoById(id);
    }

    @PutMapping("/todos")
    public TodoDto updateTodo(@RequestBody TodoDto todoDto){
        return todoService.updateTodo(todoDto);
    }

    @DeleteMapping("/todos")
    public String deleteAllTodos(){
        return todoService.deleteAllTodos();
    }
}
