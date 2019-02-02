package com.alex.springdemo.service.impl;

import com.alex.springdemo.service.IClassService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class JavaBasedPropertyBasedClassServiceImplTest {
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applications.xml");
    private static IClassService javaBasedPropertyBasedClassService = applicationContext.getBean("javaBasedPropertyBasedClassService", JavaBasedPropertyBasedClassServiceImpl.class);


    public static void main(String[] args) {
        System.out.println(javaBasedPropertyBasedClassService.getById(1));
    }
}