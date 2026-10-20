package com.example.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.example.library.Domain.Student;
import com.example.library.Repository.StudentRepository;

import org.springframework.web.bind.annotation.PostMapping;



@Controller 
public class RegisterController {
    @Autowired 
    StudentRepository repo;

@GetMapping("/signup")
public String DisplaySignup() {
    return "register.html";
}
@PostMapping("/signup")
public String signup(@ModelAttribute Student student) {
    repo.save(student);
    
    return "redirect:/index.html";
}

}
