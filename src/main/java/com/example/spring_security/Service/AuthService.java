package com.example.spring_security.Service;

import com.example.spring_security.Api.ApiException;
import com.example.spring_security.Model.Todo;
import com.example.spring_security.Model.User;
import com.example.spring_security.Repository.AuthRepository;
import com.example.spring_security.Repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final TodoRepository todoRepository;


    // authority -> ADMIN
    public List<User> getAllUsers(Integer admin_id){
        User admin = authRepository.findUserById(admin_id);
        if (admin==null)
            throw new ApiException("you are not allowed to view all users");
        return authRepository.findAllUsers();
    }

    // authority -> USER
    public void register(User user){
        user.setRole("USER");

        String hashPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hashPassword);

        authRepository.save(user);
    }

    //NO AUTHORITY
    // one admin for this system
    public void assignAdmin(){
        User check = authRepository.findAdminByUsername("sudo_ali");
        if (check!=null)
            throw new ApiException("admin is already assigned");
        User admin = new User();
        admin.setUsername("sudo_ali");
        admin.setRole("ADMIN");

        String hashPassword = new BCryptPasswordEncoder().encode("12345678");
        admin.setPassword(hashPassword);

        authRepository.save(admin);
    }

    // authority -> USER
    public void updateUser(Integer user_id, User user){
        User oldUser = authRepository.findUserById(user_id);
        if (oldUser==null)
            throw new ApiException("user not found");

        oldUser.setUsername(user.getUsername());

        String hashPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        oldUser.setPassword(hashPassword);

        authRepository.save(oldUser);
    }


    // authority -> ADMIN
    public void deleteUser(Integer admin_id, String user_username){
        User admin = authRepository.findUserById(admin_id);
        User user = authRepository.findUserByUsername(user_username);
        if (admin==null)
            throw new ApiException("admin not found");
        if (user==null)
            throw new ApiException("user not found");

        Set<Todo> todos = user.getTodos();
        todoRepository.deleteAll(todos);
        authRepository.delete(user);
    }


}
