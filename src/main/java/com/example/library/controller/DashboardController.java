package com.example.library.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.library.Domain.Student;
import com.example.library.Repository.StudentRepository;

import jakarta.servlet.http.HttpSession;

@RestController
public class DashboardController {
    private final StudentRepository repository;

    public DashboardController(StudentRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/dashboard")
    public ResponseEntity<DashboardData> getDashboard(HttpSession session) {
        Object studentId = session.getAttribute("studentId");
        if (!(studentId instanceof Long id)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Student> student = repository.findById(id);
        if (student.isEmpty()) {
            session.invalidate();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Student record = student.get();
        return ResponseEntity.ok(new DashboardData(
                record.getStudentname(),
                record.getBooks(),
                record.getFee()));
    }

    public record DashboardData(String studentname, String books, int fee) {
    }
}
