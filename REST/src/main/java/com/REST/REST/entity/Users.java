package com.REST.REST.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Indexed;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
//@Document(Collection = "users")
public class Users {

    @Id
    private Long id;

    @NonNull
    @Column(name = "username", unique = true)
    //@Indexed(unique=true), For MongoDB to have unique name
    // for the application.properties should contain spring.data.mongodb.auto-index-creation=true
     private String username;

    private LocalDate dob;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
//    @DBRef, For MongoDB
    private List<Posts> posts = new ArrayList<>();

}
