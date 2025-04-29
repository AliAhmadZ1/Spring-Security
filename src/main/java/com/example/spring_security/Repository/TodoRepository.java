package com.example.spring_security.Repository;

import com.example.spring_security.Model.Todo;
import com.example.spring_security.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo,Integer> {
    
    
    Todo findTodoByUserUsername(String username);

    Todo findTodoById(Integer id);

    List<Todo> findAllByUser(User user);
}
