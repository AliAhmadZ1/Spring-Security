package com.example.spring_security.Service;

import com.example.spring_security.Api.ApiException;
import com.example.spring_security.Model.User;
import com.example.spring_security.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;


    public List<User> getAllUsers(String admin_username){
        User admin = authRepository.findAdminByUsername(admin_username);
        if (admin==null)
            throw new ApiException("you are not allowed to view all users");
        return authRepository.findAllUsers();
    }

    public void register(User user){
        user.setRole("USER");

        String hashPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hashPassword);

        authRepository.save(user);
    }

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

    public void updateUser(String username, User user){
        User oldUser = authRepository.findUserByUsername(username);
        if (oldUser==null)
            throw new ApiException("user not found");

        oldUser.setUsername(user.getUsername());

        String hashPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        oldUser.setPassword(hashPassword);

        authRepository.save(oldUser);
    }


    public void deleteUser(String admin_username, String user_username){
        User admin = authRepository.findAdminByUsername(admin_username);
        User user = authRepository.findUserByUsername(user_username);
        if (admin==null)
            throw new ApiException("admin not found");
        if (user==null)
            throw new ApiException("user not found");

        authRepository.delete(user);
    }



}
