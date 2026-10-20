package com.example.library.Domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

class StudentTest {

    @Test
    void bindsBooksFormProperty() {
        Student student = new Student();
        BeanWrapper binder = new BeanWrapperImpl(student);

        binder.setPropertyValue("books", "The Hobbit");

        assertEquals("The Hobbit", student.getBooks());
    }
}
