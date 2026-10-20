package com.example.library.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;

import com.example.library.Domain.Student;
import com.example.library.Repository.StudentRepository;

class DashboardControllerTest {

    @Test
    void returnsTheSignedInStudentsDatabaseValues() {
        StudentRepository repository = mock(StudentRepository.class);
        DashboardController controller = new DashboardController(repository);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("studentId", 1L);

        Student student = new Student();
        student.setId(1L);
        student.setStudentname("NARESH");
        student.setBooks("DevOps");
        student.setFee(1000);
        when(repository.findById(1L)).thenReturn(Optional.of(student));

        var response = controller.getDashboard(session);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new DashboardController.DashboardData("NARESH", "DevOps", 1000), response.getBody());
        verify(repository).findById(1L);
    }

    @Test
    void rejectsDashboardRequestsWithoutASignedInStudent() {
        StudentRepository repository = mock(StudentRepository.class);
        DashboardController controller = new DashboardController(repository);
        MockHttpSession session = new MockHttpSession();

        var response = controller.getDashboard(session);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
