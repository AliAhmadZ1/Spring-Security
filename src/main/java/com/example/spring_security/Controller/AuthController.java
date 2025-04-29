package com.example.spring_security.Controller;

import com.example.spring_security.Api.ApiResponse;
import com.example.spring_security.Model.User;
import com.example.spring_security.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // authority -> ADMIN
    @GetMapping("/get-all")
    public ResponseEntity getAllUsers(@AuthenticationPrincipal User admin){
        return ResponseEntity.status(200).body(authService.getAllUsers(admin.getId()));
    }

    // PERMIT ALL
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody@Valid User user){
        authService.register(user);
        return ResponseEntity.status(200).body(new ApiResponse("user registered successfully"));
    }

    // authority -> USER
    @PutMapping("/update")
    public ResponseEntity updateUser(@AuthenticationPrincipal User authUser,@RequestBody@Valid User user){
        authService.updateUser(authUser.getId(),user);
        return ResponseEntity.status(200).body(new ApiResponse("user updated"));
    }

    // authority -> ADMIN
    @DeleteMapping("/delete/user/{username}")
    public ResponseEntity deleteUser(@AuthenticationPrincipal User admin,@PathVariable String username){
        authService.deleteUser(admin.getId(), username);
        return ResponseEntity.status(200).body(new ApiResponse("user is deleted"));
    }

    //NO AUTHORITY
    //this path well requested only one time
    @PostMapping("/assign-admin")
    public ResponseEntity assignAdmin(){
        authService.assignAdmin();
        return ResponseEntity.status(200).body(new ApiResponse("admin is assigned"));
    }

    //LOGOUT
    @GetMapping("/logout")
    public ResponseEntity logout(){
        return ResponseEntity.status(200).body(new ApiResponse("logout successfully"));
    }

}
