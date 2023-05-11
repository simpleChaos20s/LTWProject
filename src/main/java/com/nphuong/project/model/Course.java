package com.nphuong.project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course")
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "teacher_name", nullable = false, length = 45)
    private String teacherName;

    @Column(nullable = false, name = "max_quantity")
    private Integer maxQuantity;

    @Column(nullable = false)
    private Integer price;
}
