
package com.example.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;

import com.example.library.Domain.Student;
import com.example.library.Repository.StudentRepository;

@Controller
public class LoginController {

    @Autowired
    private StudentRepository repo;

    @GetMapping("/signin")
    public String showloginpage() {
        return "login";
    }

    @PostMapping("/signin")
    public String Login(
            @RequestParam String studentname,
            @RequestParam String password,
            HttpServletRequest request,
            Model model) {

        Student entity =
            repo.findByStudentnameAndPassword(studentname, password);

        if (entity == null) {
            model.addAttribute("error",
                "Invalid username or password. Please try again.");
            return "login";
        }

        if (request.getSession(false) != null) {
            request.getSession(false).invalidate();
        }

        request.getSession(true).setAttribute(
            "studentId", entity.getId());

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
