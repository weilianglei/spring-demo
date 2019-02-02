package com.alex.springdemo.service.impl;


import com.alex.springdemo.service.IClassService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FixAnnotationAndXmlClassServiceImplTest {
//    private static ApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.alex.springdemo");
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:FixConfig.xml");
    private static IClassService classService = applicationContext.getBean("fixAnnotationAndXmlClassService", FixAnnotationAndXmlClassServiceImpl.class);

    public static void main(String[] args) {
        System.out.println(classService.getById(1));
    }
}