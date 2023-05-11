package com.nphuong.project.service;

import com.nphuong.project.model.Course;
import com.nphuong.project.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired private CourseRepository repo;

    public List<Course> listAll(){
        return (List<Course>) repo.findAll();
    }
    
    public void save(Course course) {
        repo.save(course);
    }

    public Course get(Integer id) throws CourseNotFoundException {
        Optional<Course> result = repo.findById(id);
        if (result.isPresent()){
            return result.get();
        }
        throw new CourseNotFoundException("Không tìm thấy khóa học nào với ID " + id);
    }

    public void delete(Integer id) throws CourseNotFoundException {
        Long count = repo.countById(id);
        if(count == null || count ==0){
            throw new CourseNotFoundException("Không tìm thấy khóa học nào với ID " + id);
        }
        repo.deleteById(id);
    }
}
