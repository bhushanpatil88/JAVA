package com.REST.REST.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate dob;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Posts> posts;

}
