package com.alex.springdemo.service.impl;

import com.alex.springdemo.service.IClassService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class JavaBasedConstructBasedClassServiceImplTest {
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applications.xml");
    private static IClassService javaBasedConstructBasedClassService = applicationContext.getBean("javaBasedConstructBasedClassService", JavaBasedConstructBasedClassServiceImpl.class);

    public static void main(String[] args) {
        System.out.println(javaBasedConstructBasedClassService.getById(1));
    }
}