package com.example.spring_security.Controller;

import com.example.spring_security.Api.ApiResponse;
import com.example.spring_security.Model.User;
import com.example.spring_security.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/get-all/{admin_username}")
    public ResponseEntity getAllUsers(@PathVariable String admin_username){
        return ResponseEntity.status(200).body(authService.getAllUsers(admin_username));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody@Valid User user){
        authService.register(user);
        return ResponseEntity.status(200).body(new ApiResponse("user registered successfully"));
    }

    @PutMapping("/update/{username}")
    public ResponseEntity updateUser(@PathVariable String username,@RequestBody@Valid User user){
        authService.updateUser(username, user);
        return ResponseEntity.status(200).body(new ApiResponse("user updated"));
    }

    @DeleteMapping("/delete/{admin_username}/user/{username}")
    public ResponseEntity deleteUser(@PathVariable String admin_username,@PathVariable String username){
        authService.deleteUser(admin_username, username);
        return ResponseEntity.status(200).body(new ApiResponse("user is deleted"));
    }

    //this path well requested only one time
    @PostMapping("/assign-admin")
    public ResponseEntity assignAdmin(){
        authService.assignAdmin();
        return ResponseEntity.status(200).body(new ApiResponse("admin is assigned"));
    }

    @GetMapping("/logout")
    public ResponseEntity logout(){
        return ResponseEntity.status(200).body(new ApiResponse("logout successfully"));
    }

}
