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

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody@Valid User user){
        authService.register(user);
        return ResponseEntity.status(200).body(new ApiResponse("user registered successfully"));
    }

    @GetMapping("/logout")
    public ResponseEntity logout(){
        return ResponseEntity.status(200).body(new ApiResponse("logout successfully"));
    }

}
