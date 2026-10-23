package com.example.library;

import static org.mockito.Mockito.when;
// Added missing static imports for MockMvc request builders and result matchers
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.library.Domain.Student;
import com.example.library.Repository.StudentRepository;
import com.example.library.controller.RegisterController;

@WebMvcTest(RegisterController.class)
public class RegisterControllerTest {

    @Autowired 
    private MockMvc mockMvc;

    @MockitoBean 
    private StudentRepository repo;

    @Test 
    void testLoginWithCorrectCredentials() throws Exception {
        Student user = new Student("testuser", "testpassword");
        when(repo.findByStudentnameAndPassword("testuser", "testpassword")).thenReturn(user);

        mockMvc.perform(post("/login")
                .param("studentname", "testuser")
                .param("password", "testpassword"))
                .andExpect(status().isOk())
                .andExpect(content().string("Login successful Welcome, testuser!"));
    }

    @Test 
    void testLoginWithIncorrectCredentials() throws Exception {
        when(repo.findByStudentnameAndPassword("wronguser", "wrongpassword")).thenReturn(null);

        mockMvc.perform(post("/login")
                .param("studentname", "wronguser")
                .param("password", "wrongpassword"))
                .andExpect(status().isOk())
                .andExpect(content().string("Invalid username or password, Please try again"));
    }
}
