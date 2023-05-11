package com.nphuong.project.service;

import com.nphuong.project.model.Course;
import com.nphuong.project.model.Registration;
import com.nphuong.project.model.Student;
import com.nphuong.project.repository.CourseRepository;
import com.nphuong.project.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {
    @Autowired private RegistrationRepository repo;
    @Autowired private CourseRepository repoC;

    public List<Registration> listAll(){
        return (List<Registration>) repo.findAll();
    }

//    public void save(Registration registration) throws CourseNotFoundException {
//        Course course = registration.getCourse();
//        if (course != null && course.getMaxQuantity() != 0) {
//            repo.save(registration);
////            course.setMaxQuantity(course.getMaxQuantity() - 1);
////            courseService.save(course);
//        } else {
//            throw new CourseNotFoundException("Đã hết chỗ!");
//        }
//    }

    public void save(Registration registration){
        repo.save(registration);
    }

    public Registration get(Integer id) throws RegistrationNotFoundException {
        Optional<Registration> result = repo.findById(id);
        if (result.isPresent()){
            return result.get();
        }
        throw new RegistrationNotFoundException("Không tìm thấy đăng kí nào với ID " + id);
    }

    public void delete(Integer id) throws RegistrationNotFoundException {
        Long count = repo.countById(id);
        if(count == null || count ==0){
            throw new RegistrationNotFoundException("Không tìm thấy đăng kí nào với ID " + id);
        }
        repo.deleteById(id);
    }

}
