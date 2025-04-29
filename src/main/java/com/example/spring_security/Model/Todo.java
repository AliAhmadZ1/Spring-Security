package com.example.spring_security.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty
    @Column(columnDefinition = "varchar(50) not null")
    private String title;
    @NotEmpty
    @Column(columnDefinition = "varchar(100) not null")
    private String description;
    private LocalDateTime creation_time;

    @ManyToOne
    @JsonIgnore
    private User user;

}
