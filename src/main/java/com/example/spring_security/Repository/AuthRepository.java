package com.example.spring_security.Repository;

import com.example.spring_security.Model.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthRepository extends JpaRepository<User,Integer> {

    User findUserById(Integer id);

    User findUserByUsername(String username);

    @Query("select a from User a where a.role='ADMIN'")
    User findAdminByUsername(String username);

    @Query("select u from User u where u.role='USER' ")
    List<User> findAllUsers();
}
