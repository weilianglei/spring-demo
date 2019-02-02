package com.alex.springdemo.service.impl;


import com.alex.springdemo.service.IClassService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationBasedClassServiceImplTest {
    private static ApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.alex.springdemo");
    private static IClassService classService = applicationContext.getBean("annotationBasedClassService", AnnotationBasedClassServiceImpl.class);

    public static void main(String[] args) {
        System.out.println(classService.getById(1));
    }

}