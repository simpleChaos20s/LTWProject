package com.nphuong.project.repository;

import com.nphuong.project.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    public Long countById(Integer id);
}
