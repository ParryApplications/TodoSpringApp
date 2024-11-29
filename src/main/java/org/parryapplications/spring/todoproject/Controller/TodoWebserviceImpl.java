package org.parryapplications.spring.todoproject.Controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.parryapplications.spring.todoproject.customAnnotations.TimeComplexityCalculator;
import org.parryapplications.spring.todoproject.dto.ResultSet;
import org.parryapplications.spring.todoproject.dto.TodoDto;
import org.parryapplications.spring.todoproject.service.TodoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController()
//@CrossOrigin(origins = "http://localhost:3000")  //Local CORS Mapping:
@EnableMethodSecurity
public class TodoWebserviceImpl {

    private final TodoServiceImpl todoService;

    @Autowired
    public TodoWebserviceImpl(TodoServiceImpl todoService) {
        this.todoService = todoService;
    }

    //Mainly this is for health checks:
//    @GetMapping("/")
//    public String rootTodoUrl(Authentication authentication){
////        System.out.println(authentication);
//        return "Hello Oauth2";
//    }

    @PostMapping("/todos")
    @PreAuthorize("#todoDto.username == authentication.name")
    @TimeComplexityCalculator //AOP
    public ResultSet<TodoDto> createTodo(@Valid @RequestBody TodoDto todoDto) {
        return todoService.createTodo(todoDto);
    }

    @GetMapping("{username}/todos/{id}")
    @PreAuthorize("#username == authentication.name")
    @TimeComplexityCalculator //AOP
    public ResultSet<TodoDto> getTodoById(@PathVariable @NotNull @Size(min = 5) String username, @PathVariable("id") @NotNull Integer id) {
        return todoService.getTodoById(username, id);
    }

    @GetMapping("/{username}/todos")
    @PreAuthorize("#username == authentication.name")
    @TimeComplexityCalculator //AOP
    public ResultSet<List<TodoDto>> getAllTodos(@PathVariable @NotNull @Size(min = 5) String username) {
        return todoService.getAllTodos(username);
    }

    @DeleteMapping("/{username}/todos/{id}")
    @PreAuthorize("#username == authentication.name")
    @TimeComplexityCalculator //AOP
    public ResultSet<String> deleteTodoById(@PathVariable @NotNull @Size(min = 5) String username, @PathVariable("id") Integer id) {
        return todoService.deleteTodoById(username, id);
    }

    @PutMapping("/todos")
    @PreAuthorize("#todoDto.username == authentication.name")
    @TimeComplexityCalculator //AOP
    public ResultSet<TodoDto> updateTodo(@Valid @RequestBody TodoDto todoDto) {
        return todoService.updateTodo(todoDto);
    }

    @DeleteMapping("/{username}/todos")
    @PreAuthorize("#username == authentication.name")
    @TimeComplexityCalculator //AOP
    public ResultSet<String> deleteAllTodosByUsername(@PathVariable @NotNull @Size(min = 5) String username) {
        return todoService.deleteAllTodosByUsername(username);
    }

    //For Admins only:
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/allTodos")
    @TimeComplexityCalculator //AOP
    public ResultSet<String> deleteAllTodos_AdminsOnly() {
        return todoService.deleteAllTodos_AdminsOnly();
    }

    //For Admins only:
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/allTodos")
    @TimeComplexityCalculator //AOP
    public ResultSet<List<TodoDto>> getAllTodos_AdminsOnly() {
        return todoService.getAllTodos_AdminsOnly();
    }
}
