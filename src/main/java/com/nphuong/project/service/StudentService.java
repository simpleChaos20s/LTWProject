package com.nphuong.project.service;

import com.nphuong.project.model.Student;
import com.nphuong.project.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repo;

    //liet ke tat ca student in db
    public List<Student> listAll(){
        return (List<Student>) repo.findAll();
    }
    public List<Student> listAllAcceptAdmin() {
        return repo.findAllByMsvNot("admin");
    }

    public void save(Student student) {
        repo.save(student);
    }

    public Student get(Integer id) throws StudentNotFoundException {
        Optional<Student> result = repo.findById(id);
        if (result.isPresent()){
            return result.get();
        }
        throw new StudentNotFoundException("Không tìm thấy bất kì sinh viên nào với ID " + id);
    }

    public void delete(Integer id) throws StudentNotFoundException {
        Long count = repo.countById(id);
        if(count == null || count ==0){
            throw new StudentNotFoundException("Không tìm thấy bất kì sinh viên nào với ID " + id);
        }
        repo.deleteById(id);
    }

    public Student getByMSV(String msv) throws StudentNotFoundException {
        Optional<Student> result = repo.findStudentByMSV(msv);
        if (result.isPresent()){
            return result.get();
        }
        throw new StudentNotFoundException("Tài khoản không tồn tại!");
    }
}
