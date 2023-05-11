package com.nphuong.project.repository;

import com.nphuong.project.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RegistrationRepository extends JpaRepository<Registration,Integer> {
    public Long countById(Integer id);
}
