package com.nphuong.project.controller;

import com.nphuong.project.model.Course;
import com.nphuong.project.model.Registration;
import com.nphuong.project.model.Student;
import com.nphuong.project.repository.CourseRepository;
import com.nphuong.project.repository.StudentRepository;
import com.nphuong.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RegistrationController {
    @Autowired
    private RegistrationService service;
    @Autowired
    private StudentService serviceS;
    @Autowired
    private CourseService serviceC;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired private CourseRepository courseRepository;

    @GetMapping("/student/registry/{sid}/{cid}")
    public String showConfirmForm(@PathVariable("sid") Integer sid,
                                  @PathVariable("cid") Integer cid,
                                  Model model,RedirectAttributes ra) {
        try {
            Student student = serviceS.get(sid);
            Course course = serviceC.get(cid);
            Registration regist = new Registration();
            regist.setStudent(student);
            regist.setCourse(course);
            model.addAttribute("registration", regist);
            model.addAttribute("pageTitle", "Xác nhận đăng kí");
            return "student/confirm_form";
        } catch (StudentNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/student/courses";
        } catch (CourseNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/student/courses";
        }

    }

    @PostMapping("/registration/save")
    public String saveRegistration(@ModelAttribute("registration") Registration registration,
                                   Model model, RedirectAttributes ra) {
        Student student = registration.getStudent();
        String id = String.valueOf(student.getId());
        registration.setStudent(student);
        Course course = registration.getCourse();
        registration.setCourse(course);
        //nếu sv đã đk r thì ko đk dc nữa
        List<Registration> listRegist = service.listAll();
        for (Registration r:listRegist){
            if (r.getStudent().getId() == student.getId()
                    && r.getCourse().getId() == course.getId()){
                ra.addFlashAttribute("message", "Bạn đã đăng kí khóa dọc này rồi!");
                return "redirect:/student/courses/" + id;
            }
        }
        //nếu khóa học còn chỗ thì mới đk đc
        if (course.getMaxQuantity()  != 0){
            course.setMaxQuantity(course.getMaxQuantity()-1);
            serviceC.save(course);
            service.save(registration);
            ra.addFlashAttribute("message", "Đăng kí thành công!");
        }else{
            ra.addFlashAttribute("message", "Lớp đã đủ người!");
        }
        return "redirect:/student/courses/" + id;
    }


    //danh sách sv đăng kí 1 môn học nào đó
    @GetMapping("/courses/registers/{cid}")
    public String showStudentRegistList(@PathVariable("cid") Integer id,Model model){
        List<Registration> listRegist = service.listAll();
        List<Student> listStudents = new ArrayList<>();
        try {
            Course course = serviceC.get(id);
            for (Registration r: listRegist){
                if(r.getCourse().getId() == id){
                    listStudents.add(r.getStudent());
                }
            }
        } catch (CourseNotFoundException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("listStudentRegist", listStudents);
        return "admin/student_registration";
    }

}
