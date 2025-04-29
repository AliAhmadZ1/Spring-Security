package com.example.spring_security.Controller;

import com.example.spring_security.Api.ApiResponse;
import com.example.spring_security.Model.Todo;
import com.example.spring_security.Model.User;
import com.example.spring_security.Service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;


    // authority -> ADMIN
    @GetMapping("/get-all")
    public ResponseEntity getAllTodos(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(todoService.getAllTodos(user.getId()));
    }


    // authority -> USER
    @GetMapping("/get-my-todos")
    public ResponseEntity getMyTodos(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(todoService.getMyTodos(user.getId()));
    }

    // authority -> USER
    @PostMapping("/add-todo")
    public ResponseEntity addTodo(@AuthenticationPrincipal User user, @RequestBody@Valid Todo todo){
        todoService.addTodo(user.getId(), todo);
        return ResponseEntity.status(200).body(new ApiResponse("new todo created"));
    }

    // authority -> USER
    @PutMapping("/update-todo/{todo_id}")
    public ResponseEntity updateTodo(@AuthenticationPrincipal User user, @PathVariable Integer todo_id, @RequestBody@Valid Todo todo){
        todoService.updateTodo(user.getId(), todo_id,todo);
        return ResponseEntity.status(200).body(new ApiResponse("todo updated"));
    }

    // authority -> USER
    @DeleteMapping("/delete-todo/{todo_id}")
    public ResponseEntity deleteTodo(@AuthenticationPrincipal User user, @PathVariable Integer todo_id){
        todoService.deleteTodo(user.getId(), todo_id);
        return ResponseEntity.status(200).body(new ApiResponse("todo deleted"));
    }


}
