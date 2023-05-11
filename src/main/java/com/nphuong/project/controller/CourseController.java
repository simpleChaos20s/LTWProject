package com.nphuong.project.controller;

import com.nphuong.project.model.Course;
import com.nphuong.project.model.Student;
import com.nphuong.project.service.CourseNotFoundException;
import com.nphuong.project.service.CourseService;
import com.nphuong.project.service.StudentNotFoundException;
import com.nphuong.project.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CourseController {
    @Autowired private CourseService service;
    @Autowired private StudentService serviceS;

    @GetMapping("/courses")
    public String showCourseList(Model model){
        List<Course> listCourses = service.listAll();
        model.addAttribute("listCourses", listCourses);
        return "admin/courses";
    }

//    @GetMapping("/student/courses")
//    public String showCourseRegistryList(Model model){
//        List<Course> listCoursesRegis = service.listAll();
//        model.addAttribute("listCoursesRegis", listCoursesRegis);
//        return "student/courses";
//    }


    @GetMapping("/student/courses/{userid}")
    public String listCourses(@PathVariable("userid") Integer id, Model model) throws StudentNotFoundException {
        List<Course> listCoursesRegis = service.listAll();
        Student stu = serviceS.get(id);
        model.addAttribute("listCoursesRegis", listCoursesRegis);
        model.addAttribute("student",stu);
        return "student/courses";
    }


    @GetMapping("/courses/new")
    public String showAddNewForm(Model model){
        model.addAttribute("course", new Course());
        model.addAttribute("pageTitle", "Thêm khóa học");
        return "admin/course_form";
    }

    @PostMapping("/courses/save")
    public String saveCourse(Course course, RedirectAttributes ra){
        service.save(course);
        ra.addFlashAttribute("message", "Lưu thông tin khóa học thành công!");
        return "redirect:/courses";
    }

    @GetMapping("/courses/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra){
        try {
            Course course = service.get(id);
            model.addAttribute("course", course);
            model.addAttribute("pageTitle", "Sửa thông tin khóa học (ID: " + id + ")");

            return "admin/course_form";
        } catch (CourseNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/courses";
        }
    }

    @GetMapping("/courses/delete/{id}")
    public String deleteStudent(@PathVariable("id") Integer id, RedirectAttributes ra){
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "Đã xóa khóa học có ID " + id);
        } catch (CourseNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/courses";
    }

//    @GetMapping("/courses/registers/{cid}")
//    public String getRegistrationList(@PathVariable("cid") Integer id, Student student){
//
//    }

}
