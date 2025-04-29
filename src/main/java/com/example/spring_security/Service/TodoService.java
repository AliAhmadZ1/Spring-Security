package com.example.spring_security.Service;

import com.example.spring_security.Api.ApiException;
import com.example.spring_security.Model.Todo;
import com.example.spring_security.Model.User;
import com.example.spring_security.Repository.AuthRepository;
import com.example.spring_security.Repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final AuthRepository authRepository;


    // authority -> ADMIN
    public List<Todo> getAllTodos(Integer user_id){
        User user = authRepository.findUserById(user_id);
        if (user==null)
            throw new ApiException("user not found");
        if (user.getRole().equals("USER"))
            throw new ApiException("view all todos not allowed. you don't have permission");
        return todoRepository.findAll();
    }

    // authority -> USER
    public List<Todo> getMyTodos(Integer user_id) {
        User user = authRepository.findUserById(user_id);
        if (user == null)
            throw new ApiException("user not found");

        List<Todo> todos = todoRepository.findAllByUser(user);

        return todos;
    }

    // authority -> USER
    public void addTodo(Integer user_id, Todo todo) {
        User user = authRepository.findUserById(user_id);
        if (user == null)
            throw new ApiException("user not found");
        todo.setUser(user);
        todo.setCreation_time(LocalDateTime.now());
        todoRepository.save(todo);
    }

    // authority -> USER
    public void updateTodo(Integer user_id, Integer todo_id, Todo todo) {
        Todo oldTodo = todoRepository.findTodoById(todo_id);
        if (oldTodo == null)
            throw new ApiException("todo not found");
        if (oldTodo.getUser().getId() != user_id)
            throw new ApiException("this todo not related to your account");

        oldTodo.setTitle(todo.getTitle());
        oldTodo.setDescription(todo.getDescription());
        todoRepository.save(oldTodo);
    }

    // authority -> USER
    public void deleteTodo(Integer user_id, Integer todo_id) {
        User user = authRepository.findUserById(user_id);
        Todo todo = todoRepository.findTodoById(todo_id);

        if (user == null)
            throw new ApiException("user not found");
        if (todo == null)
            throw new ApiException("todo not found");
        if (todo.getUser().getId() != user.getId())
            throw new ApiException("this todo not related to your account");

//        user.getTodos().remove(todo);
        todoRepository.delete(todo);
    }


}
