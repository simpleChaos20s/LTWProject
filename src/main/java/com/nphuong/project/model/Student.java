package com.nphuong.project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "students")
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false, unique = true, length = 15)
    private String msv;

    @Column(nullable = false, length = 15)
    private String password;

    @Column(nullable = false,length = 45, name = "first_name")
    private String firstName;

    @Column(nullable = false,length = 45, name = "last_name")
    private String lastName;

    @Column(nullable = false,length = 45, name = "class_name")
    private String className;

    private Integer gender;
}
