
package com.example.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;

import com.example.library.Domain.Student;
import com.example.library.Repository.StudentRepository;

@Controller
public class LoginController {

    @Autowired
    StudentRepository repo;

    // Display login page
    @GetMapping("/signin")
    public String showloginpage() {
        return "login.html";
    }

    // Validate login credentials
    @PostMapping("/signin")
    public String Login(
            @RequestParam String studentname,
            @RequestParam String password,
            HttpServletRequest request,
            Model model) {

        Student entity = repo.findByStudentnameAndPassword(
                studentname, password);

        if (entity == null) {
            model.addAttribute("error",
                    "Invalid username or password. Please try again.");

            return "login.html";
        }

        // Create student session
        request.getSession().invalidate();

        request.getSession(true).setAttribute(
                "studentId", entity.getId());

        return "redirect:/Dashboard.html";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        if (request.getSession(false) != null) {
            request.getSession(false).invalidate();
        }

        return "redirect:/index.html";
    }
}
