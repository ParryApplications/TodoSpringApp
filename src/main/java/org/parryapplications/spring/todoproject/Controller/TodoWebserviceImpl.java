package org.parryapplications.spring.todoproject.Controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.parryapplications.spring.todoproject.dto.TodoDto;
import org.parryapplications.spring.todoproject.model.Todo;
import org.parryapplications.spring.todoproject.service.TodoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController()
//@CrossOrigin(origins = "http://localhost:3000")  //Local CORS Mapping:
public class TodoWebserviceImpl {

    private final TodoServiceImpl todoService;

    @Autowired
    public TodoWebserviceImpl(TodoServiceImpl todoService){
        this.todoService = todoService;
    }

    @PostMapping("/todos")
    public TodoDto createTodo(@Valid @RequestBody TodoDto todoDto){
        return todoService.createTodo(todoDto);
    }

    @GetMapping("{username}/todos/{id}")
    public TodoDto getTodoById(@PathVariable @NotNull @Size(min = 5) String username, @PathVariable("id") @NotNull Integer id){
        return todoService.getTodoById(username, id);
    }

    @GetMapping("/{username}/todos")
    public List<TodoDto> getAllTodos(@PathVariable @NotNull @Size(min = 5) String username){
        return todoService.getAllTodos(username);
    }

    @DeleteMapping("/{username}/todos/{id}")
    public void deleteTodoById(@PathVariable @NotNull @Size(min = 5) String username, @PathVariable("id") Integer id){
        todoService.deleteTodoById(username, id);
    }

    @PutMapping("/todos")
    public TodoDto updateTodo(@Valid @RequestBody TodoDto todoDto){
        return todoService.updateTodo(todoDto);
    }

    @DeleteMapping("/{username}/todos")
    public String deleteAllTodosByUsername(@PathVariable @NotNull @Size(min = 5) String username){
        return todoService.deleteAllTodosByUsername(username);
    }

    //For Admins only:
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/todos")
    public String deleteAllTodos(){
        return todoService.deleteAllTodos();
    }
}
