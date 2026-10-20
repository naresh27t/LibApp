package com.example.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;

import com.example.library.Domain.Student;
import com.example.library.Repository.StudentRepository;

import org.springframework.web.bind.annotation.PostMapping;



@Controller 
public class LoginController {
    @Autowired 
    StudentRepository repo;

@GetMapping("/signin")
public String showloginpage() {
    return "login.html";
}
@PostMapping("/signin")
public String Login(@RequestParam String studentname, @RequestParam String password, HttpServletRequest request) {
    Student entity = repo.findByStudentnameAndPassword(studentname, password);
    if (entity == null) {
        return "redirect:/signin";
    }
    request.getSession().invalidate();
    request.getSession(true).setAttribute("studentId", entity.getId());
    return "redirect:/Dashboard.html";
}
@GetMapping("/logout")
public String logout(HttpServletRequest request) {
    if (request.getSession(false) != null) {
        request.getSession(false).invalidate();
    }
    return "redirect:/index.html";
}
}
