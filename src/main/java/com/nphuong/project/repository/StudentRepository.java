package com.nphuong.project.repository;

import com.nphuong.project.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Integer> {
    public Long countById(Integer id);

    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);

    @Query("SELECT s FROM Student s WHERE s.msv = ?1")
    Optional<Student> findStudentByMSV(String msv);

    @Query("SELECT s FROM Student s WHERE s.msv <> 'admin'")
    List<Student> findAllByMsvNot(String msv);
}
