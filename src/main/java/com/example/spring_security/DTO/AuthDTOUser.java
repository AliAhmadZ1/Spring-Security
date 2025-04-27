package com.example.spring_security.DTO;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
public class AuthDTOUser {

    @NotEmpty(message = "username cannot be empty")
    @Size(min = 4,max = 20,message = "username should be in 4-20 length size letters")
    @Column(columnDefinition = "varchar(20) not null")
    @Check(constraints = "length(username)>=4 and length(username)<=20")
    private String username;
    @NotEmpty(message = "password cannot be empty")
    @Pattern(regexp = "^([a-z]|[A-Z]|[0-9])+$")
    @Size(min = 8,max = 16,message = "password should be 8 character length to 16")
    @Column(columnDefinition = "varchar(255) not null")
    @Check(constraints = "length(password)>=8 and length(password)<=16")
    private String password;

}
