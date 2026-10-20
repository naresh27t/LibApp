package com.example.library.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.library.Domain.Student;

@Repository 
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByStudentnameAndPassword(String studentname, String password);

}
