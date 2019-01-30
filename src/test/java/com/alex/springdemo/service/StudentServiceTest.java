package com.alex.springdemo.service;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StudentServiceTest {

    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applications.xml");
    private static StudentService studentService = (StudentService)applicationContext.getBean("studentService");
    public static void main(String[] args) {
        System.out.println(studentService.getById(1L));
    }
}