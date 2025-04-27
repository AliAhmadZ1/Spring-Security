package com.example.spring_security.Repository;

import com.example.spring_security.Model.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<User,Integer> {

    User findUserById(Integer id);

    User findUserByUsername(String username);
}
