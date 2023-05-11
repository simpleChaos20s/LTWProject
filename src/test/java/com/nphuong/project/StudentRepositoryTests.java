package com.nphuong.project;

import com.nphuong.project.model.Course;
import com.nphuong.project.model.Registration;
import com.nphuong.project.model.Student;
import com.nphuong.project.repository.CourseRepository;
import com.nphuong.project.repository.RegistrationRepository;
import com.nphuong.project.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class StudentRepositoryTests {
    @Autowired private StudentRepository repo;
    @Autowired private CourseRepository repoc;
    @Autowired private RegistrationRepository repor;

    @Test
    public void testAddNew(){
        Student student = new Student();
        student.setEmail("dang@gmail.com");
        student.setMsv("B20DCCN123");
        student.setClassName("D20CQCN10");
        student.setPassword("123456");
        student.setFirstName("Dang");
        student.setLastName("Tran");

        Student savedStudent = repo.save(student);

        Assertions.assertThat(savedStudent).isNotNull();
        Assertions.assertThat(savedStudent.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll(){
        Iterable<Student> students = repo.findAll();
        Assertions.assertThat(students).hasSizeGreaterThan(0);

        for (Student student:students){
            System.out.println(student);
        }
    }

    @Test
    public void testUpdate(){
        Integer stuId = 2;
        Optional<Student> optionalStudent = repo.findById(stuId);
        Student student = optionalStudent.get();
        student.setMsv("B20DCCN123");
        repo.save(student);

        Student updatedStudent = repo.findById(stuId).get();
        Assertions.assertThat(updatedStudent.getMsv()).isEqualTo("B20DCCN123");
    }

    @Test
    public void testGet(){
        Integer stuId = 2;
        Optional<Student> optionalStudent = repo.findById(stuId);
        Assertions.assertThat(optionalStudent).isPresent();
        System.out.println(optionalStudent.get());
    }

    @Test
    public void testDelete(){
        Integer stuId = 2;
        repo.deleteById(stuId);

        Optional<Student> optionalStudent = repo.findById(stuId);
        Assertions.assertThat(optionalStudent).isNotPresent();
    }

    @Test
    public void testDeleteCourse(){
        Integer Id = 6;
        repoc.deleteById(Id);

        Optional<Course> optionalCourse = repoc.findById(Id);
        Assertions.assertThat(optionalCourse).isNotPresent();
    }

    @Test
    public void testAddNewCourse(){
        Course course = new Course();
        course.setName("Toán rời rạc");
        course.setTeacherName("Trần Đình Ánh");
        course.setPrice(1605);
        course.setMaxQuantity(54);

        Course savedCourse = repoc.save(course);

        Assertions.assertThat(savedCourse).isNotNull();
        Assertions.assertThat(savedCourse.getId()).isGreaterThan(0);
    }


    @Test
    public void testDeleteRegis(){
        Integer Id = 15;
        repor.deleteById(Id);

        Optional<Registration> optionalRegistration = repor.findById(Id);
        Assertions.assertThat(optionalRegistration).isNotPresent();
    }

    @Test
    public void testAddNewRegis(){
        Registration r = new Registration();
        Student student = repo.getById(1);
        Course course = repoc.getById(1);
        r.setStudent(student);
        r.setCourse(course);

        Registration sr= repor.save(r);
        Assertions.assertThat(sr).isNotNull();
        Assertions.assertThat(sr.getId()).isGreaterThan(0);
    }
}
