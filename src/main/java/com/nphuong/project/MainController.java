package com.nphuong.project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("")
    public String showHomePage(){
        return "index";
    }

    @GetMapping("/home")
    public String showHomePage1(){
        return "index";
    }

    @GetMapping("/exitad")
    public String showAdminHomePage(){
        return "index";
    }

    @GetMapping("/exitstu")
    public String showStuHomePage(){
        return "index";
    }

    @GetMapping("/adhome")
    public String showAdminHomePage1(){
        return "admin/index";
    }

    @GetMapping("/stuhome")
    public String showStudentHomePage1(){
        return "student/index";
    }
}
