
package com.example.library;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.library.Domain.Student;
import com.example.library.Repository.StudentRepository;
import com.example.library.controller.LoginController;

@WebMvcTest(LoginController.class)
public class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentRepository repo;

    // Test 1: Login with correct credentials
    @Test
    void testLoginWithCorrectCredentials() throws Exception {

        Student user = new Student("testuser", "testpassword");

        // Set ID because LoginController stores it in the session
        user.setId(1L);

        when(repo.findByStudentnameAndPassword(
                "testuser", "testpassword"))
                .thenReturn(user);

        mockMvc.perform(post("/signin")
                .param("studentname", "testuser")
                .param("password", "testpassword"))

                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/Dashboard.html"));
    }

    // Test 2: Login with incorrect credentials
    @Test
    void testLoginWithIncorrectCredentials() throws Exception {

        when(repo.findByStudentnameAndPassword(
                "wronguser", "wrongpassword"))
                .thenReturn(null);

        mockMvc.perform(post("/signin")
                .param("studentname", "wronguser")
                .param("password", "wrongpassword"))

                .andExpect(status().isOk())
                .andExpect(view().name("login.html"))
                .andExpect(model().attributeExists("error"));
    }
}
