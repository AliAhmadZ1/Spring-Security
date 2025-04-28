package com.example.spring_security.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "username cannot be empty")
    @Size(min = 4,max = 20,message = "username should be in 4-20 length size letters")
    @Column(columnDefinition = "varchar(20) not null")
    @Check(constraints = "length(username)>=4 and length(username)<=20")
    private String username;
    @NotEmpty(message = "password cannot be empty")
//    @Pattern(regexp = "^([a-z]|[A-Z]|[0-9])+$")
//    @Size(min = 8,max = 16,message = "password should be 8 character length to 16")
    @Column(columnDefinition = "varchar(255) not null")
//    @Check(constraints = "length(password)>=8 and length(password)<=16")
    private String password;
    @Pattern(regexp = "^(ADMIN|USER)$")
    @Column(columnDefinition = "varchar(5) not null")
    @Check(constraints = "role='ADMIN' or role='USER'")
    private String role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Todo> todos;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
