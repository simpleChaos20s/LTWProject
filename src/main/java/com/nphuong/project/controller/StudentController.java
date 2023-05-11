package com.nphuong.project.controller;

import com.nphuong.project.model.Student;
import com.nphuong.project.service.StudentNotFoundException;
import com.nphuong.project.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class StudentController {
    @Autowired
    private StudentService service;

    @GetMapping("/students")
    public String showStudentList(Model model){
        List<Student> listStudents = service.listAllAcceptAdmin();
        model.addAttribute("listStudents", listStudents);
        return "admin/students";
    }

    @GetMapping("/students/new")
    public String showAddNewForm(Model model){
        model.addAttribute("student", new Student());
        model.addAttribute("pageTitle", "Thêm sinh viên");
        return "admin/student_form";
    }

    @PostMapping("/students/save")
    public String saveStudent(Student student, RedirectAttributes ra){
        service.save(student);
        ra.addFlashAttribute("message", "Lưu thông tin sinh viên thành công!");
        return "redirect:/students";
    }

    @GetMapping("/students/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra){
        try {
            Student student = service.get(id);
            model.addAttribute("student", student);
            model.addAttribute("pageTitle", "Sửa thông tin sinh viên (ID: " + id + ")");

            return "admin/student_form";
        } catch (StudentNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/students";
        }
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable("id") Integer id, RedirectAttributes ra){
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "Đã xóa sinh viên có ID " + id);
        } catch (StudentNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/students";
    }



    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("student", new Student());
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@ModelAttribute("student") Student stu, Model model, RedirectAttributes ra) {
        try {
            Student existingStudent = service.getByMSV(stu.getMsv());
            if(existingStudent != null && existingStudent.getMsv().equals("admin")
                    && existingStudent.getPassword().equals(stu.getPassword())){
                return "admin/index";
            }
            if (existingStudent != null && existingStudent.getPassword().equals(stu.getPassword())) {
                model.addAttribute("studentLogged", existingStudent);
                model.addAttribute("studentID", existingStudent.getId());
                model.addAttribute("studentName", existingStudent.getFirstName());
                return "student/index";
            }
            ra.addFlashAttribute("message", "Tài khoản hoặc mật khẩu không đúng!");
            return "redirect:/login";
        } catch (StudentNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/login";
        }
    }

}
